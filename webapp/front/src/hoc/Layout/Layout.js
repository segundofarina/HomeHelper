import React, {Fragment} from 'react'
import Navbar from '../../components/Navigation/Navbar/Navbar'
import Footer from '../../components/Navigation/Footer/Footer'
import styles from './Layout.module.css'

const Layout = props => (
    <Fragment>
        <Navbar/>
        <div className={styles.MainContainer} >
            {props.children}
        </div>
        <Footer />
    </Fragment>
)

export default Layout