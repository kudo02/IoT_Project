import time
import paho.mqtt.client as mqtt
import random

# Thiết lập thông tin Mosquitto broker
broker_address = "10.20.58.131" 
port = 1883  # Port mặc định của MQTT
topic = "sensor"
topic1 = "sensor/temperature"
topic2 = "sensor/humidity"
topic3 = "sensor/light"

# Tạo một client MQTT
client = mqtt.Client()

# Kết nối tới Mosquitto broker
client.connect(broker_address, port=port)




try:
    while True:
        a = round(random.uniform(10,20),2)
        b = round(random.uniform(10,100),2)
        c = round(random.uniform(10,100),2)

        client.publish(topic, "1234")
        client.publish(topic1, str(a))
        client.publish(topic2, str(b))
        client.publish(topic3, str(c))

        time.sleep(2)
 
except KeyboardInterrupt:
    print("Kết thúc chương trình")

# Ngắt kết nối với Mosquitto broker
client.disconnect()
