const {createApp}= Vue
console.log("gucadss")
const app=createApp({
    data(){
        return{
            products:[],
            product:'',
            descriptionSearched:'',
            descriptionProduct:'',
            statusProduct:'',
            quantityProduct:'',
            priceBuy:'',
            sellingPrice:'',
            minimumStock:'',
            maximumStock:'',
            includesIVA:'',
            select:[true, false],
            descriptionSearchedM:'',
            descriptionProductM:'',
            statusProductM:'',
            quantityProductM:'',
            priceBuyM:'',
            sellingPriceM:'',
            minimumStockM:'',
            maximumStockM:'',
            includesIVAM:'',
            
        }
    },
    created(){
        this.getProducts()
    },
    methods:{
        getProducts(){
            axios.get('/api/products')
            .then(response=>{
                this.products=response.data
            })
            .catch(error=>{console.log(error)})
        },
        modifyProduct(){
            //this.product=this.products.find(product=>product.id==id);
            if(this.descriptionProductM.length == 0){
                this.descriptionProductM=this.product.descriptionProduct;
            }if(this.statusProductM.length == 0){
                this.statusProductM==this.product.statusProduct;
            }if(this.quantityProductM.length == 0){
                this.quantityProductM==this.product.cantidadProduct;
            }if(this.priceBuyM.length == 0){
                this.priceBuyM==this.product.precioCompra;
            }if(this.sellingPriceM.length == 0){
                this.sellingPriceM==this.product.precioVenta;
            }if(this.minimumStockM.length == 0){
                this.minimumStockM==this.product.minimumStock;
            }if(this.maximumStockM.length == 0){
                this.maximumStockM==this.product.maximumStock;
            }if(this.includesIVAM.length == 0){
                this.includesIVAM==this.product.includesIVA;
            }
            axios.put('/api/products/modifyProduct',`id=${this.product.id}&descriptionProduct=${this.descriptionProductM}&statusProduct=${this.statusProductM}&cantidadProduct=${this.quantityProductM}&precioCompra=${this.priceBuyM}&precioVenta=${this.sellingPriceM}&minimumStock=${this.minimumStockM}&maximumStock=${this.maximumStockM}&includesIVA=${this.includesIVAM}`)
            .then(
                Swal.fire({
                    title:'Maked!',
                    text:'you changed the information',
                    icon:'success',
                    didOpen:()=>{
                        document.querySelector('.swal2-confirm').addEventListener('click', () =>{window.location.href='/products.html'})
                    }
                }) 
            )
            .catch(function(error){
                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: error.response.data,
                   
                  })
            })
        },
    },
})
app.mount('#app')