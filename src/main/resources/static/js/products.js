const {createApp}= Vue
console.log("gucadss")
const app=createApp({
    data(){
        return{
            products:[],
            filteredProducts:[],
            wordSearch:'',
            descriptionProduct:'',
            quantityProduct:'',
            priceBuy:'',
            sellingPrice:'',
            minimumStock:'',
            maximumStock:'',
            includesIVA:'',
            select:[true, false],
            
        }
    },
    created(){
        this.getProducts()
    },
    methods:{
        getProducts(){
            axios.get('/api/products')
            .then(response=>{
                this.products=response.data.filter(product=>product.statusProduct)
                this.filteredProducts=response.data.filter(product=>product.statusProduct)
                console.log(this.products)
            })
            .catch(error=>{console.log(error)})
        },
        deleteProduct(id){
            console.log(id)
            axios.patch('/api/products/state',`id=${id}`)
            .then(response=>{
                Swal.fire({
                    title:'Maked!',
                    text:'you deleted the product',
                    icon:'success',
                    didOpen:()=>{
                        document.querySelector('.swal2-confirm').addEventListener('click', () =>{window.location.href='/products.html'})
                    }
                }) 
            })
            .catch(function(error){
                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: error.response.data,
                   
                  })
            })
        },
        newProduct(){
            axios.post('/api/products',`descriptionProduct=${this.descriptionProduct}&cantidadProduct=${this.quantityProduct}&precioCompra=${this.priceBuy}&precioVenta=${this.sellingPrice}&minimumStock=${this.minimumStock}&maximumStock=${this.maximumStock}&includesIVA=${this.includesIVA}`)
            .then(response=>{
                Swal.fire({
                    title:'Maked!',
                    text:'you created a new product',
                    icon:'success',
                    didOpen:()=>{
                        document.querySelector('.swal2-confirm').addEventListener('click', () =>{window.location.href='/products.html'})
                    }
                }) 
            })
            .catch(function(error){
                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: error.response.data,
                   
                  })
            })
        },
        search(){
            console.log("entro")
            this.filteredProducts=this.products.filter(product=>product.descriptionProduct.toLowerCase().includes(this.wordSearch.toLowerCase()))
            console.log(this.filteredProducts)
        }
    }
})
app.mount('#app')