<script setup>
import { onMounted, ref } from 'vue';

const myChart = ref(null); // 用于存储ECharts实例
const pieChart = ref(null); // 用于存储ECharts实例
// 初始化ECharts图表
function initChart() {
  var chartContainer = document.getElementById('chart');
  myChart.value = echarts.init(chartContainer);

  var option = {
    title: {
      text: '活跃度',
      textStyle: {
        color: '#FFFFFF' // 白色文字
      }
    },
    xAxis: {
      data: [12.28, 12.29, 12.30, 12.31, 1.1, 1.2, 1.3, 1.4, 1.5, 1.6,1.7,1.8,1.9,1.10,1.11,1.12,1.13,1.14,1.15,1.16,1.17,1.18,1.19,1.20,1.21,1.22,1.23,1.24,1.25,1.26],
      axisLabel: {
        color: '#FFFFFF' // 白色文字
      }
    },
    yAxis: {axisLabel: {
        color: '#FFFFFF' // 白色文字
      }},
    series: [{
      color: '#FFA600',
      name: '数值',
      type: 'bar',
      data: [31, 57, 90, 52, 62, 65, 32, 29, 97, 48, 87, 42, 45, 70, 55, 37, 72, 77, 67, 75, 100, 60, 40, 80, 95, 85, 82, 50, 92, 35]
    }]
  };

  myChart.value.setOption(option);

}
function initPieChart() {
  var pieContainer = document.getElementById('pie-chart');
  pieChart.value = echarts.init(pieContainer);

  var pieOption = {

    title: {
      text: '饼状图示例',
      left: 'center',
      textStyle: {
        color: '#FFFFFF' // 标题颜色为白色
      }
    },
    tooltip: {
      trigger: 'item',
      textStyle: {
        color: '#FFFFFF' // 提示框文字颜色为白色
      }
    },
    legend: {
      orient: 'vertical',
      left: 'left',
      textStyle: {
        color: '#FFFFFF' // 图例文字颜色为白色
      }
    },
    series: [
      {
        name: '访问来源',
        type: 'pie',
        radius: '50%',
        data: [
          { value: 1048, name: '搜索引擎' },
          { value: 735, name: '直接访问' },
          { value: 580, name: '邮件营销' },
          { value: 484, name: '联盟广告' },
          { value: 300, name: '视频广告' }
        ],
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        },
        label: {
          color: '#FFFFFF' // 数据标签文字颜色为白色
        },
        labelLine: {
          lineStyle: {
            color: '#FFFFFF' // 饼图连接线颜色为白色
          }
        }
      }
    ]
  };

  pieChart.value.setOption(pieOption);
}
function exportToExcel() {
  var customers = [

    { custID: "4", email: "20212005041@qq.com", sname: "黄思潮", sex: "Male", mobile: "14475865678", address: "广东省 佛山 华南师范大学南海校区", zip: "121211" },
  ];

  var members = [
    { email: "member1@example.com", password: "password1", mname: "Mike", mobile: "111111111", address: "Address 3", jifen: "100", ye: "50" },
  ];

  var customerSheet = XLSX.utils.json_to_sheet(customers);
  var memberSheet = XLSX.utils.json_to_sheet(members);

  var wb = XLSX.utils.book_new();
  XLSX.utils.book_append_sheet(wb, customerSheet, "Customers");
  XLSX.utils.book_append_sheet(wb, memberSheet, "Members");

  var wbout = XLSX.write(wb, { bookType: 'xlsx', type: 'binary' });

  function s2ab(s) {
    var buf = new ArrayBuffer(s.length);
    var view = new Uint8Array(buf);
    for (var i = 0; i < s.length; i++) {
      view[i] = s.charCodeAt(i) & 0xFF;
    }
    return buf;
  }

  saveAs(new Blob([s2ab(wbout)], { type: "application/octet-stream" }), "table.xlsx");
}
onMounted(async () => {
  initChart();
  initPieChart();
});
</script>

<template>
  <div class="main">
    <el-main>
      <el-button @click="exportToExcel">导出为Excel</el-button>
      <div id="chart" style="width: 600px; height: 400px;"></div>
      <div id="pie-chart" style="width: 600px; height: 400px;"></div>
    </el-main>
  </div>
</template>

<style lang="scss" scoped>
.main {
  margin: auto;
  display: flex;
}

.module-hot {
  max-width: calc(4 * 190px);
}

.el-main {
  width: calc(210px * 4);
  overflow: hidden;
}

.el-aside {
  width: 30vw;
  padding: 20px;
  overflow: hidden;
}
</style>
