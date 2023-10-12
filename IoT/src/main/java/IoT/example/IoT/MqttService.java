package IoT.example.IoT;

import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class MqttService {
    @Autowired
    private DataService service;

    @Autowired
    private StatusService statusService;

    private final String mqttServer = "tcp://172.20.10.14:1883";
    private final String mqttClientId = "172.20.10.14";
    private final String[] mqttTopics = {"sensor/temperature", "sensor/humidity", "sensor/light", "led1", "led2", "led3", "sensor/bui"};
    private MqttClient mqttClient;

    private Map<String, String> currentStatus = new HashMap<>();

    public MqttService() {
        try {
            mqttClient = new MqttClient(mqttServer, mqttClientId);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(false); // Để duy trì kết nối khi không có session
            mqttClient.connect(connOpts);

            for (String topic : mqttTopics) {
                mqttClient.subscribe(topic);
            }

            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable throwable) {
                    System.out.println("Connection to MQTT Broker lost!");
                }

                private float temperature = 0;
                private float humidity = 0;
                private float light = 0;

                private int bui = 0;

                @Override
                public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
//                    System.out.println(topic);
                    String message = new String(mqttMessage.getPayload());
                    if (topic.equals("sensor/temperature")) {
                        temperature = Float.parseFloat(message);
                    } else if (topic.equals("sensor/humidity")) {
                        humidity = Float.parseFloat(message);
                    } else if (topic.equals("sensor/light")) {
                        light = Float.parseFloat(message);
                    } else if (topic.equals("sensor/bui")) {
                        bui = Integer.parseInt(message);
                    }
                    if (topic.equals("led2") || topic.equals("led1") || topic.equals("led3")) {
                        String currentMessage = currentStatus.getOrDefault(topic, "");
                        if (!message.equals(currentMessage)) {
                            Status status = new Status();
                            status.setName(String.valueOf(topic));
                            status.setStatus(String.valueOf(message));
                            status.setTime(String.valueOf(new Date()));

                            // Sử dụng một tiến trình hoặc luồng bổ sung để lưu trạng thái
                            new Thread(() -> {
                                statusService.save(status);
                                // Cập nhật trạng thái hiện tại sau khi đã lưu
                                currentStatus.put(topic, message);
                            }).start();
                        }
                    }


                    // Kiểm tra xem tất cả ba giá trị đã được nhận
                    if (temperature != 0 && humidity != 0 && light != 0 && bui != 0) {
                        Data data = new Data();
                        data.setTemperature(Float.valueOf(temperature));
                        data.setHumidity(Float.valueOf(humidity));
                        data.setLight(Float.valueOf(light));
                        data.setBui(Float.valueOf(bui));
                        // Định dạng thời gian theo "yyyy/MM/dd HH:mm:ss"
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        String formattedDate = sdf.format(new Date());

                        data.setTimestamp(formattedDate);
                        service.save(data);

//                        System.out.println("Temperature: " + temperature + " °C");
//                        System.out.println("Humidity: " + humidity + " %");
//                        System.out.println("Light: " + light + " lux");

                        // Đặt lại các giá trị về 0 để chờ nhận giá trị mới
                        temperature = 0;
                        humidity = 0;
                        light = 0;
                        bui = 0;
                    }

                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                    // This method is not used in this example
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    // Để dừng kết nối MQTT khi cần thiết
    public void stop() {
        try {
            mqttClient.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    // gửi trạng thái đèn sang Arduino
    public void updateLampStatus(LampDTO lampDTO) throws MqttException {
        String lamp = lampDTO.getLamp();
        String status = lampDTO.getStatus();
        MqttMessage message = new MqttMessage(status.getBytes());
        mqttClient.publish(lamp, message);
//        System.out.println(lamp);
//        System.out.println(status);
    }

}

