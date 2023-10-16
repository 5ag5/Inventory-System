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
            hostAddress: 'http://localhost:8080/',
            reportsInformation:[],
            entitiesReport:undefined,
            reportsOptions:undefined,
            reportChosen:undefined,
            entityReportChosen:undefined
        }
    },

    created(){
        this.getData()
    },

    methods:{
        async getData(){
            axios.get('http://localhost:8080/api/graphs/graphsInformation').then(val =>{
                //console.log(val.data)
                this.graphsInformation = val.data
                console.log(val.data)
                this.choiceGraph = "Pie Chart"; 
                this.entitySelected = "Users";
                let parameter = "User Type";

                this.generateGraph(parameter)
            })

            axios.get('http://localhost:8080/api/reports/getReportsInformation').then( val =>{
                this.reportsInformation = val.data;
                this.entitiesReport = new Set() 
                
                this.reportsInformation.forEach( val =>{
                    this.entitiesReport.add(val.entityType)
                })
                console.log(this.reportsInformation)
            })

 //====================================GRAPHICS===========================================       

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

        },

 //====================================REPORTS===========================================       
    captureEntityReport(entity){
        this.entityReportChosen = entity
        this.reportsOptions = new Set()
        this.reportsInformation.forEach( val =>{
            if(val.entityType === entity){
                this.reportsOptions.add(val.reportType)
            }
        })
    },

    captureTypeReport(report){
        let urlApi = ""
        let fileName = ""
        this.reportsInformation.forEach( val =>{
            if(val.entityType === this.entityReportChosen && val.reportType === report){
                urlApi = val.urlType
                fileName =val.fileName
            }
        })  
        console.log(urlApi)
        console.log(this.hostAddress + urlApi)
        console.log(fileName)

        if(report === "Excel"){
        axios.get(this.hostAddress + urlApi,{responseType:'blob'}).then(val =>{
            const url = window.URL.createObjectURL(new Blob([val.data]));
            const a = document.createElement('a');
            a.href = url;
            a.setAttribute('download',fileName);
            document.body.appendChild(a);
            a.click();
            document.body.removeChild(a);
        })
        .catch(error => {
            console.error("Error while fetching the PDF:", error);
        });
        //console.log('SOY UN EXCEL QUE FUNCIONA')
        }else if(report === "PDF"){
            axios.post(this.hostAddress + urlApi,null,{responseType:'blob'}).then(val =>{
            const url = window.URL.createObjectURL(new Blob([val.data]));
            const a = document.createElement('a');
            a.href = url;
            a.setAttribute('download',fileName);
            document.body.appendChild(a);
            a.click();
            document.body.removeChild(a);
            })
            .catch(error => {
                console.error("Error while fetching the PDF:", error);
            });
        }
    }
    }
})

app.mount('#app')