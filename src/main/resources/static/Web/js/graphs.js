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
            optionsEntitySelected: [],
            pieChartTest: [],
        }
    },

    created(){
        this.getData()
    },

    methods:{
        async getData(){
        axios.get('http://localhost:8080/api/graphs/UserGraphsCount/User Type')
            .then(val =>{
                this.valuesXline = val.data[0];
                this.valuesYline = val.data[1]
                const arrayNums = this.stringToNumbers(this.valuesYline)

                this.createLineChart(this.valuesXline, arrayNums)
                this.cretePieChart()
            
            })

            axios.get('http://localhost:8080/api/graphs/optionsPieChart').then(val =>{
                this.mapOptionsPie = new Map(Object.entries(val.data));
                console.log( this.mapOptionsPie);
            })

            axios.get('http://localhost:8080/api/graphs/optionsLineGraphs').then(val =>{
                this.mapOptionsLine = new Map(Object.entries(val.data));
                console.log(this.mapOptionsLine);
            })

            axios.get('http://localhost:8080/api/graphs/UserPieGraph/Status').then( val =>{
                this.pieChartTest = val.data
                console.log(val.data)
                console.log(this.pieChartTest)
                
               this.cretePieChart(this.pieChartTest)
            })

        },

        TypeOfGraphSelected(graph){
            switch(graph){
                case "Pie Chart":
                this.choiceGraph = "Pie Chart";  
                console.log(this.choiceGraph)

                break;
                case "Line Chart":
                this.choiceGraph = "Line Chart";  
                console.log(this.choiceGraph)
                break;
            }
        },

        typeOfEntitySelected(option){
            console.log(option)
            console.log(this.choiceGraph)

            switch(option){
                case "Users":
                    //this.entitySelected = "User";
                    console.log('Si entro') 
                    if(this.choiceGraph === "Pie Chart"){
                        console.log('Si entro al if') 
                        //console.log(this.mapOptionsPie.get('Users'));
                        this.optionsEntitySelected = mapOptionsLine.get("Users");
                        console.log(this.optionsEntitySelected)
                    }else if (this.choiceGraph === "Line Chart") {
                        this.optionsEntitySelected = mapOptionsLine.get("Users");
                        console.log(this.optionsEntitySelected)
                    } 
                break;
                case "Products":
                    //this.entitySelected = "Product";  
                    if(this.choiceGraph === "Pie Chart"){
                        this.optionsEntitySelected = mapOptionsPie.get("Products");
                        console.log(this.optionsEntitySelected)
                    }else if (this.choiceGraph === "Line Chart") {
                        this.optionsEntitySelected = mapOptionsLine.get("Products");
                        console.log(this.optionsEntitySelected)
                    } 
                break;
                case "Audits":
                    //this.entitySelected = "Audit";  
                    if(this.choiceGraph === "Pie Chart"){
                        this.optionsEntitySelected = mapOptionsPie.get("Audits");
                        console.log(this.optionsEntitySelected)
                    }else if (this.choiceGraph === "Line Chart") {
                        this.optionsEntitySelected = mapOptionsLine.get("Audits");
                        console.log(this.optionsEntitySelected)
                    } 
                break;
            }
        },
        
        generateGraphUsers(value, entity){
            if(entity === ""){

            }
        },

        generateGraphProducts(value){

        },

        generateGraphAudits(value){

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

        cretePieChart(dataObjects){
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