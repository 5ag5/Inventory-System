const {createApp} = Vue

const app = createApp({
    data(){
        return{
            users: undefined,
            valuesXline: [],
            valuesYline: []
        }
    },

    created(){
        this.getData()
    },

    methods:{
        async getData(){
        axios.get('http://localhost:8080/api/graphs/UserGraphsCount/User Type')
            .then(elemento =>{
                console.log(elemento.data)
                this.valuesXline = elemento.data[0];
                this.valuesYline = elemento.data[1]
                console.log(this.valuesXline)
                console.log(this.valuesYline)
                const arrayNums = this.stringToNumbers(this.valuesYline)

                this.createLineChart(this.valuesXline, arrayNums)
                this.cretePieChart()
            })
        },

        stringToNumbers(arrStr){
            const arrNum = arrStr.map(str =>{
                return parseInt(str, 10)
            }) 
            
            return arrNum
        },

        createLineChart(category, dataSeries){
                Highcharts.chart('GraphInfo', {
                    chart: {
                        type: 'line'
                    },
                    title: {
                        text: 'Fruit Consumption'
                    },
                    xAxis: {
                        categories: category
                        // ['Apples', 'Bananas', 'Oranges','Strawberries','Coconuts']
                    },
                    tooltip:{
                        formatter: function(){
                            console.log(this);
                        }
                    },
                    series: [{
                        data: dataSeries
                        // [10, 20, 34,25,16]
                    }]
                })
        },

        cretePieChart(){
        Highcharts.chart('DIVdownloadParameter', { 
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false,
                type: 'pie'
            },
            title: {
                text: 'Browser market shares in May, 2020',
                align: 'left'
            },
            tooltip: {
                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
            },
            accessibility: {
                point: {
                    valueSuffix: '%'
                }
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true,
                        format: '<b>{point.name}</b>: {point.percentage:.1f} %'
                    }
                }
            },
            series: [{
                name: 'Brands',
                colorByPoint: true,
                data: [{
                    name: 'Chrome',
                    y: 70.67,
                    sliced: true,
                    selected: true
                }, {
                    name: 'Edge',
                    y: 14.77,
                    sliced: false,
                    selected: false
                },  {
                    name: 'Firefox',
                    y: 4.86,
                    sliced: false,
                    selected: false
                }, {
                    name: 'Safari',
                    y: 2.63,
                    sliced: false,
                    selected: false
                }, {
                    name: 'Internet Explorer',
                    y: 1.53,
                    sliced: false,
                    selected: false
                },  {
                    name: 'Opera',
                    y: 1.40,
                    sliced: false,
                    selected: false
                }, {
                    name: 'Sogou Explorer',
                    y: 0.84,
                    sliced: false,
                    selected: false
                }, {
                    name: 'QQ',
                    y: 0.51,
                    sliced: false,
                    selected: false
                }, {
                    name: 'Other',
                    y: 2.6,
                    sliced: false,
                    selected: false
                }]
            }]
        });

        }
    }
})

app.mount('#app')