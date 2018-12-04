import React, {Component} from 'react'
import { withRouter } from 'react-router-dom'
import Search from '../../components/Home/Search/Search'
import style from './Home.module.css'

class Home extends Component {

    render() {
        return (
            <div className={style.Container}>
                <div className={style.MainContent}>
                    <h1>Bienvenido a Home Helper!</h1>
                    <Search 
                        className={style.Search} />
                </div>
                <div className={style.Background}></div>
            </div>
       )
    }
}

export default withRouter(Home);