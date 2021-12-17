window.onload = () => {
  const ctx = document.getElementById('almoney-chart').getContext('2d');
  axios
    .get('/member/bank/indexAjax?act=GRAPH')
    .then(({ data }) => {
      const { earning } = data.result;

      console.log(earning);

      for (let i = 1; i <= 6; i++) {
        if (!earning.hasOwnProperty(i)) earning[i] = 0;
      }

      let chartData = {
        labels: Object.keys(earning)
          .map(iter => `${iter}주 전`)
          .reverse(),
        datasets: [
          {
            label: '수익',
            backgroundColor: '#228be6',
            borderColor: '#228be6',
            borderWidth: 1,
            data: Object.values(earning).reverse(),
            fill: false,
          },
        ],
      };

      renderAlmoneyChart(ctx, chartData);
    })
    .catch(err => {
      console.error(err);
    });
};

const renderAlmoneyChart = (canvas, data) => {
  new Chart(canvas, {
    type: 'line',
    data: data,
    options: {
      legend: {
        display: false,
      },
      responsive: true,
      scales: {
        xAxes: [
          {
            display: true,
          },
        ],
        yAxes: [
          {
            type: 'linear',
            display: true,
            position: 'left',
            scaleLabel: {
              display: false,
              labelString: '수익',
            },
          },
        ],
      },
    },
  });
};
