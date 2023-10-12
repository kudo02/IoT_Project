// Cập nhật dữ liệu mỗi 5 giây (hoặc tần suất mong muốn)
setInterval(() => {
    $.ajax({
        url: "/get-latest-7-data", // Đặt URL tương ứng với API của bạn
        type: "GET",
        success: function (data) {
            // Tách dữ liệu thành các mảng riêng biệt cho nhiệt độ, độ ẩm và độ sáng
            const temperatures = data.map(item => parseFloat(item.temperature));
            const humiditys = data.map(item => parseFloat(item.humidity));
            const brightness = data.map(item => parseFloat(item.light));
            // const labels = data.map(item => item.time); // Sử dụng thời gian làm nhãn
            const labels = ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']

            // Tạo dữ liệu mới cho biểu đồ
            const newData = {
                labels: labels,
                datasets: [{
                    label: 'Nhiệt độ',
                    data: temperatures,
                    fill: false,
                    borderColor: '#dc0000'
                },
                    {
                        label: 'Độ ẩm',
                        data: humiditys,
                        fill: false,
                        borderColor: '#a5b6c4'
                    },
                    {
                        label: 'Độ sáng',
                        data: brightness,
                        fill: false,
                        borderColor: 'rgb(75, 192, 192)'
                    }]
            };

            // Cập nhật biểu đồ
            const ctx = document.getElementById('myChart');
            ctx.style.width = '100%';
            ctx.style.height = '100%';

            // if (myChart) {
            //     myChart.destroy(); // Hủy biểu đồ cũ trước khi vẽ biểu đồ mới
            // }

            myChart = new Chart(ctx, {
                type: 'line',
                data: newData,
                options: {
                    animations: {
                        x: {
                            easing: 'linear'
                        },
                        y: {
                            type: 'number',
                            easing: 'linear'
                        }
                    },
                    scales: {
                        x: {
                            reverse: true // Thêm tùy chọn reverse cho trục x
                        }
                    },
                    title: {
                        display: true,
                        text: 'Biểu đồ'
                    }
                }
            });
        }
    });
}, 2000);


// // Cập nhật dữ liệu mỗi 5 giây (hoặc tần suất mong muốn)
// setInterval(() => {
//     $.ajax({
//         url: "/get-latest-7-bui", // Đặt URL tương ứng với API của bạn
//         type: "GET",
//         success: function (data) {
//             // Tách dữ liệu thành các mảng riêng biệt cho nhiệt độ, độ ẩm và độ sáng
//             const temperatures = data;
//             // const labels = data.map(item => item.time); // Sử dụng thời gian làm nhãn
//             const labels = ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
//
//             // Tạo dữ liệu mới cho biểu đồ
//             const newData = {
//                 labels: labels,
//                 datasets: [{
//                     label: 'Độ bụi',
//                     data: temperatures,
//                     fill: false,
//                     borderColor: '#dc0000'
//                 }]
//             };
//
//             // Cập nhật biểu đồ
//             const ctx = document.getElementById('myChart1');
//             ctx.style.width = '100%';
//             ctx.style.height = '100%';
//
//             // if (myChart) {
//             //     myChart.destroy(); // Hủy biểu đồ cũ trước khi vẽ biểu đồ mới
//             // }
//
//             myChart = new Chart(ctx, {
//                 type: 'line',
//                 data: newData,
//                 options: {
//                     animations: {
//                         x: {
//                             easing: 'linear'
//                         },
//                         y: {
//                             type: 'number',
//                             easing: 'linear'
//                         }
//                     },
//                     scales: {
//                         x: {
//                             reverse: true // Thêm tùy chọn reverse cho trục x
//                         }
//                     },
//                     title: {
//                         display: true,
//                         text: 'Biểu đồ'
//                     }
//                 }
//             });
//         }
//     });
// }, 2000);
