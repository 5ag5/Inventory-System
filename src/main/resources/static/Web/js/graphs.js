const {createApp} = Vue

const app = createApp({
    data(){
        return{
            users: undefined,
            mapOptionsPie: undefined,
            mapOptionsLine: undefined,
            optionsEntity: undefined,
            valuesXline: [],
            valuesYline: [],
            choiceGraph: undefined,
            entitySelected: undefined,
            optionsEntitySelected: [],
            parameterOptions: [],
            graphOptionChosen: undefined,
            graphsInformation:[],
            hostAddress: 'http://localhost:8080/'
        }
    },

    created(){
        this.getData()
    },

    methods:{
        async getData(){
        axios.get('http://localhost:8080/api/graphs/UserGraphsCount/User Type')
            .then(val =>{
                /*this.valuesXline = val.data[0];
                this.valuesYline = val.data[1]
                const arrayNums = this.stringToNumbers(this.valuesYline)

                this.createLineChart(this.valuesXline, arrayNums, val.titleGraph)*/
            })

           axios.get('http://localhost:8080/api/graphs/UserPieGraph/Status').then( val =>{
                this.pieChartTest = val.data

                this.createPieChart(this.pieChartTest, val.titleGraph);
            })

            axios.get('http://localhost:8080/api/graphs/optionsTests').then(val =>{
                //console.log(val.data)
                this.graphsInformation = val.data
                console.log(val.data)

            })

        },

        TypeOfGraphSelected(graph){
            switch(graph){
                case "Pie Chart":
                this.choiceGraph = "Pie Chart";  

                break;
                case "Line Chart":
                this.choiceGraph = "Line Chart";  
                break;
            }
        },

        typeOfEntitySelected(option){
            this.entitySelected = option

            this.graphsInformation.forEach( val => {
                if(val.entityType === option && this.choiceGraph === val.typeOfGraph ){
                    this.parameterOptions = val.optionsEntity
                }
            })
        },
        
        generateGraph(parameter){
            let apiUrl = ""
            let title = ""

            this.graphsInformation.forEach(val => {
                if(val.entityType === this.entitySelected && val.typeOfGraph === this.choiceGraph){
                    apiUrl = val.urlAPI
                    title = val.titleGraph
                }
            })

            console.log(this.hostAddress + apiUrl+ parameter)
           axios.get(this.hostAddress + apiUrl+ parameter).then( val => {
                console.log(val.data)
                if(this.choiceGraph === "Pie Chart" ){
                    this.createPieChart(val.data, title)
                }else if(this.choiceGraph === "Line Chart" ){
                    this.valuesXline = val.data[0];
                    this.valuesYline = val.data[1]
                    const arrayNums = this.stringToNumbers(this.valuesYline)

                    this.createLineChart(this.valuesXline, arrayNums, title)
                }
            })
            
        },

        stringToNumbers(arrStr){
            const arrNum = arrStr.map(str =>{
                return parseInt(str, 10)
            }) 
            
            return arrNum
        },

        createLineChart(category, dataSeries, title){
                Highcharts.chart('GraphInfo', {
                    chart: {
                        type: 'line'
                    },
                    title: {
                        text: title
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

        createPieChart(dataObjects, title){
        Highcharts.chart('GraphInfo', { 
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false,
                type: 'pie'
            },
            title: {
                text: title,
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
                data: dataObjects
                
                /*[{
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
                }]*/
            }]
        });

        }
    }
})

app.mount('#app')