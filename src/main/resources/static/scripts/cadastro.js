let checkbox = document.getElementById('check')
let campos = document.getElementsByClassName('usada')

function mostrarKM(){
    for(let i = 0; i < campos.length; i++){
        if(campos[i].style.display === 'block' || campos.display === ''){
            campos[i].style.display = 'none'
        }else{
            campos[i].style.display = 'block'
        }
    }
}