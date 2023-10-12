package IoT.example.IoT;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
class LampController {

    @Autowired
    private MqttService service;
    private Map<String, String> lampStatuses = new HashMap<>();

    // Mặc định khi trang web được tải, cả hai đèn đều tắt (status = off)
    @PostConstruct
    public void init() {
        lampStatuses.put("lamp1", "on");
        lampStatuses.put("lamp2", "off");
    }

    @GetMapping("/get-lamp-status")
    public Map<String, String> getLampStatus() {
        Map<String, String> response = new HashMap<>();
        response.put("status", lampStatuses.get("lamp1"));
        // Để lấy trạng thái đèn 2, cần triển khai tương tự
        // response.put("lamp2Status", lampStatuses.get("lamp2"));
        return response;
    }

    @PostMapping("/update-lamp-status")
    public Map<String, String> updateLampStatus(@RequestBody Map<String, String> request) throws MqttException {
        String lampName = request.get("name");
        String status = request.get("status");
        lampStatuses.put(lampName, status);
        LampDTO lampDTO = new LampDTO(lampName, status);
        service.updateLampStatus(lampDTO);

        // Gửi yêu cầu đến Arduino để bật/tắt đèn (cần triển khai)

        Map<String, String> response = new HashMap<>();
        response.put("success", "true");
        return response;
    }

}

