function updateData() {
    // Sử dụng AJAX để gửi yêu cầu lấy dữ liệu từ API
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var data = JSON.parse(xhr.responseText);

            // Cập nhật màu nền và hiển thị nhiệt độ
            changeBackgroundColorAndTemperature(data.temperature);
            // Cập nhật màu nền và hiển thị độ ẩm
            changeBackgroundColorAndHumidity(data.humidity);
            // Cập nhật màu nền và hiển thị độ sáng
            changeBackgroundColorAndBrightness(data.light);

            changeBackgroundColorAndBui(data.bui);

        }
    };
    xhr.open("GET", "/get-latest-arduino-data", true); // Sử dụng URL tương ứng với API của bạn
    xhr.send();
}

// Gọi hàm updateData() để cập nhật dữ liệu ban đầu
updateData();

// Cập nhật dữ liệu mỗi 5 giây (hoặc tần suất mong muốn)
setInterval(updateData, 5000);


// Hàm để thay đổi màu nền và hiển thị nhiệt độ
// function changeBackgroundColorAndTemperature(temperature) {
//     var card = document.querySelector('.temperature');
//     var temperatureDisplay = document.querySelector('.text-gray-800.text-center span');
//
//     if (temperature > 30) {
//         card.style.backgroundColor = '#dc0000';
//     } else if (temperature > 10 && temperature < 30) {
//         card.style.backgroundColor = 'rgb(220, 140, 0)';
//     } else {
//         card.style.backgroundColor = 'rgb(11, 188, 218,0.64 )';
//     }
//
//     temperatureDisplay.textContent = temperature + ' °C'; // Cập nhật nhiệt độ hiển thị
// }

function changeBackgroundColorAndTemperature(temperature) {
    var card = document.querySelector('.temperature');
    var temperatureDisplay = document.querySelector('.text-gray-800.text-center span');

    // Kiểm tra nhiệt độ và đặt màu nền tương ứng
    if (temperature > 30) {
        // Nếu nhiệt độ vượt quá 50 độ, thiết lập màu đỏ và bắt đầu nhấp nháy
        var blinkingInterval = setInterval(function () {
            if (card.style.backgroundColor === 'red') {
                card.style.backgroundColor = ''; // Bỏ màu đỏ
            } else {
                card.style.backgroundColor = 'red'; // Đặt màu đỏ
            }
        }, 500); // Nhấp nháy mỗi 500ms

        // Cập nhật nhiệt độ hiển thị
        temperatureDisplay.textContent = temperature + ' °C';

        // Để dừng nhấp nháy sau một khoảng thời gian, bạn có thể sử dụng setTimeout hoặc clearInterval
        // setTimeout(function() {
        //     clearInterval(blinkingInterval);
        // }, 5000); // Dừng nhấp nháy sau 5 giây (5000ms) hoặc sử dụng clearInterval(blinkingInterval) khi cần dừng

    } else if (temperature > 30) {
        card.style.backgroundColor = '#dc0000';
        temperatureDisplay.textContent = temperature + ' °C';
    } else if (temperature > 10 && temperature < 30) {
        card.style.backgroundColor = 'rgb(220, 140, 0)';
        temperatureDisplay.textContent = temperature + ' °C';
    } else {
        card.style.backgroundColor = 'rgb(11, 188, 218, 0.64)';
        temperatureDisplay.textContent = temperature + ' °C';
    }
}


// Thay đổi nhiệt độ và màu nền dựa trên nhiệt độ mới

// Hàm để thay đổi màu nền và hiển thị độ ẩm
// function changeBackgroundColorAndHumidity(humidity) {
//     var card = document.querySelector('.humidity');
//     var humidityDisplay = document.querySelector('.humidityDisplay');
//
//     if (humidity > 70) {
//         card.style.backgroundColor = 'rgb(23, 17, 204)';
//     } else if (humidity >= 30 && humidity <= 70) {
//         card.style.backgroundColor = '#2f9ecc';
//     } else {
//         card.style.backgroundColor = 'rgb(17, 197, 204)';
//     }
//
//     humidityDisplay.textContent = humidity + ' %'; // Cập nhật độ ẩm hiển thị
// }

function changeBackgroundColorAndHumidity(humidity) {
    var card = document.querySelector('.humidity');
    var humidityDisplay = document.querySelector('.humidityDisplay');

    // Kiểm tra độ ẩm và đặt màu nền tương ứng
    if (humidity > 70) {
        // Nếu độ ẩm vượt quá 60, thiết lập màu xanh và bắt đầu nhấp nháy
        var blinkingInterval = setInterval(function () {
            if (card.style.backgroundColor === 'green') {
                card.style.backgroundColor = ''; // Bỏ màu xanh
            } else {
                card.style.backgroundColor = 'green'; // Đặt màu xanh
            }
        }, 500); // Nhấp nháy mỗi 500ms

        // Cập nhật độ ẩm hiển thị
        humidityDisplay.textContent = humidity + ' %';

        // Để dừng nhấp nháy sau một khoảng thời gian, bạn có thể sử dụng setTimeout hoặc clearInterval
        // setTimeout(function() {
        //     clearInterval(blinkingInterval);
        // }, 5000); // Dừng nhấp nháy sau 5 giây (5000ms) hoặc sử dụng clearInterval(blinkingInterval) khi cần dừng

    } else if (humidity > 70) {
        card.style.backgroundColor = 'rgb(23, 17, 204)';
        humidityDisplay.textContent = humidity + ' %';
    } else if (humidity >= 30 && humidity <= 70) {
        card.style.backgroundColor = '#2f9ecc';
        humidityDisplay.textContent = humidity + ' %';
    } else {
        card.style.backgroundColor = 'rgb(17, 197, 204)';
        humidityDisplay.textContent = humidity + ' %';
    }
}


// Thay đổi nhiệt độ và màu nền dựa trên độ ẩm mới

// Hàm để thay đổi màu nền và hiển thị độ sáng
function changeBackgroundColorAndBrightness(brightness) {
    var card = document.querySelector('.brightness');
    var brightnessDisplay = document.querySelector('.brightnessDisplay');

    // Kiểm tra độ sáng và đặt màu nền tương ứng
    if (brightness > 700) {
        // Nếu độ sáng vượt quá 600, thiết lập màu xanh dương và bắt đầu nhấp nháy
        var blinkingInterval = setInterval(function () {
            if (card.style.backgroundColor === 'blue') {
                card.style.backgroundColor = ''; // Bỏ màu xanh dương
            } else {
                card.style.backgroundColor = 'blue'; // Đặt màu xanh dương
            }
        }, 500); // Nhấp nháy mỗi 500ms

        // Cập nhật độ sáng hiển thị
        brightnessDisplay.textContent = brightness + ' lux';

        // Để dừng nhấp nháy sau một khoảng thời gian, bạn có thể sử dụng setTimeout hoặc clearInterval
        // setTimeout(function() {
        //     clearInterval(blinkingInterval);
        // }, 5000); // Dừng nhấp nháy sau 5 giây (5000ms) hoặc sử dụng clearInterval(blinkingInterval) khi cần dừng

    } else if (brightness > 500) {
        card.style.backgroundColor = '#4e73df';
    } else if (brightness >= 300 && brightness <= 500) {
        card.style.backgroundColor = '#36b9cc';
    } else {
        card.style.backgroundColor = '#1cc88a';
    }

    brightnessDisplay.textContent = brightness + ' lux'; // Cập nhật độ sáng hiển thị
}

// function changeBackgroundColorAndBrightness(brightness) {
//     var card = document.querySelector('.brightness');
//     var brightnessDisplay = document.querySelector('.brightnessDisplay');
//
//     // Kiểm tra độ sáng và đặt màu nền tương ứng
//     if (brightness > 600) {
//         // Nếu độ sáng vượt quá 600, thiết lập màu xanh dương và bắt đầu nhấp nháy
//         var blinkingInterval = setInterval(function () {
//             if (card.style.backgroundColor === '#0000FF') {
//                 card.style.backgroundColor = ''; // Bỏ màu xanh dương
//             } else {
//                 card.style.backgroundColor = '#0000FF'; // Đặt màu xanh dương
//             }
//         }, 500); // Nhấp nháy mỗi 500ms
//
//         // Cập nhật độ sáng hiển thị
//         brightnessDisplay.textContent = brightness + ' lux';
//
//         // Để dừng nhấp nháy sau một khoảng thời gian, bạn có thể sử dụng setTimeout hoặc clearInterval
//         // setTimeout(function() {
//         //     clearInterval(blinkingInterval);
//         // }, 5000); // Dừng nhấp nháy sau 5 giây (5000ms) hoặc sử dụng clearInterval(blinkingInterval) khi cần dừng
//
//     } else if (brightness > 500) {
//         card.style.backgroundColor = '#4e73df';
//         brightnessDisplay.textContent = brightness + ' lux';
//     } else if (brightness >= 300 && brightness <= 500) {
//         card.style.backgroundColor = '#36b9cc';
//         brightnessDisplay.textContent = brightness + ' lux';
//     } else {
//         card.style.backgroundColor = '#1cc88a';
//         brightnessDisplay.textContent = brightness + ' lux';
//     }
// }


// function changeBackgroundColorAndBui(bui) {
//     var card = document.querySelector('.bui');
//     var BuiDisplay = document.querySelector('.BuiDisplay');
//
//     if (BuiDisplay >= 7) {
//         card.style.backgroundColor = '#dc0000';
//     } else if (BuiDisplay > 3 && BuiDisplay < 7) {
//         card.style.backgroundColor = 'rgb(220, 140, 0)';
//     } else {
//         card.style.backgroundColor = 'rgb(11, 188, 218,0.64 )';
//     }
//
//     BuiDisplay.textContent = bui + ' mg/m3'; // Cập nhật độ bụi hiển thị
// }





