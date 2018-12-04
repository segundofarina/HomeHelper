import React from 'react'
import Panel from '../../UI/Panel/Panel'
import Rating from'../../UI/rating/Rating'
import ProgressBar from '../../UI/progressBar/progressBar'

const Calification = (props)=>{
    let detailedCalifications = null;
    if(props.general>0){
        detailedCalifications =
        <div> 
            <h5>Calidad:</h5>
            <ProgressBar value={props.quality*20} label={props.quality}/>
            <h5>Precio:</h5>
            <ProgressBar value={props.price*20} label={props.price}/>
            <h5>Puntualidad:</h5>
            <ProgressBar value={props.punctuality*20} label={props.punctuality}/>
            <h5>Trato:</h5>
            <ProgressBar value={props.treatment*20} label={props.treatment}/>
            <h5>Liempiza:</h5>
            <ProgressBar value={props.cleanness*20} label={props.cleanness}/>
        </div>
    }

    return(
        <Panel>
            <h4>Califiación General</h4>
            <Rating value={props.general}/>
            {detailedCalifications}
        </Panel>
    )
}
export default Calification