package IoT.example.IoT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.DataTruncation;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class Controller1 {
    @Autowired
    private DataService dataService;

    @Autowired
    private StatusService service1;

    @Autowired
    private MqttService mqttService;
    private Map<String, String> lampStatuses = new HashMap<>();

    @RequestMapping("")
    public String showAdmin() {
        return "index";
    }

    @RequestMapping("/data1")
    public String showAdmin1() {
        return "abc";
    }

    //    @GetMapping("/history")
//    public String showHistory(Model model, @RequestParam(name = "pageNo", defaultValue = "1") Integer page) {
//
//        Page<Data> list = this.dataService.getAll(page);
//
//        model.addAttribute("totalPage", list.getTotalPages());
//        model.addAttribute("currentPage", page);
//        model.addAttribute("list", list);
//
//        return "history";
//    }
    @GetMapping("/history")
    public String showHistory(
            Model model,
            @RequestParam(name = "pageNo", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "sortColumn", required = false) String sortColumn,
            @RequestParam(name = "sortOrder", required = false) String sortOrder
    ) {
        // Xác định cách sắp xếp mặc định nếu không có tham số
        if (sortColumn == null || sortColumn.isEmpty()) {
            sortColumn = "id"; // Sắp xếp mặc định theo cột id
            sortOrder = "asc";
        }

        // Lấy tất cả dữ liệu từ cơ sở dữ liệu và sắp xếp nó theo yêu cầu
        List<Data> dataList = dataService.getAllSorted(sortColumn, sortOrder);

        // Tính toán trang hiện tại và chia dữ liệu thành từng trang
        int pageSize = 20;
        int totalItems = dataList.size();
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);

        List<Data> currentPageData = dataList.stream()
                .skip((page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());

        model.addAttribute("totalPage", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("sortColumn", sortColumn);
        model.addAttribute("sortOrder", sortOrder);
        model.addAttribute("list", currentPageData);

        return "history";
    }


    @GetMapping("/status")
    public String showStatus(Model model, @RequestParam(name = "pageNo", defaultValue = "1") Integer page) {
        Page<Status> list = this.service1.getAll(page);
        model.addAttribute("totalPage", list.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("list", list);
        return "status";
    }

    @GetMapping("/get-latest-arduino-data")
    public ResponseEntity<Data> getLatestArduinoData() {
        // Lấy giá trị cuối cùng từ cơ sở dữ liệu
        Data latestData = dataService.getLatestData(); // Thay đổi cách bạn truy vấn dữ liệu từ DataService

        if (latestData != null) {
            return new ResponseEntity<>(latestData, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get-latest-7-data")
    public ResponseEntity<List<Data>> getLatest7Data() {
        List<Data> latest7Data = dataService.getLatest7Data();
        return new ResponseEntity<>(latest7Data, HttpStatus.OK);
    }

//    @GetMapping("/get-latest-7-bui")
//    public ResponseEntity<List<Float>> getLatest7Bui() {
//        List<Data> allData = dataService.getList(); // Lấy tất cả dữ liệu
//        int size = allData.size();
//        int startIndex = Math.max(size - 7, 0); // Xác định vị trí bắt đầu để lấy 7 giá trị cuối cùng
//
//        List<Float> latest7Bui = allData.subList(startIndex, size)
//                .stream()
//                .map(Data::getBui) // Lấy thuộc tính temperature từ mỗi đối tượng Data
//                .collect(Collectors.toList());
//
//        Collections.reverse(latest7Bui); // Đảo ngược danh sách để có thứ tự đúng
//        return new ResponseEntity<>(latest7Bui, HttpStatus.OK);
//    }

//    @GetMapping("/data")
//    public List<Data> getDataBetweenTimeStamps(@RequestParam("startTime") String startTimeStamp, @RequestParam("endTime") String endTimeStamp) {
//        return dataService.getDataBetweenTimeStamps(startTimeStamp, endTimeStamp);
//    }

//    @GetMapping("/data/filter")
//    public List<Data> getDataByTimeStampRange(
//            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss") Date startDate,
//            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss") Date endDate) {
//
//        return dataService.findByTimeStampBetween(startDate, endDate);
//    }
}

