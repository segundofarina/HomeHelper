import React, { Component } from 'react'
import styles from './Progress.module.css'
import MultiButton from '../../../components/UI/MultiButton/MultiButton'
import Scores from '../../../components/Provider/Progress/Scores/Scores'
import Reviews from '../../../components/Provider/Progress/Reviews/Reviews'
import { connect } from 'react-redux'
import * as userDataActions from '../../../store/actions/userDataActions'

class Progress extends Component {
    state = {
        showingSection: 1,
    }

    componentWillMount() {
        if(!this.props.showingProvider) {
            this.props.setUsingProvider()
        }
    }

    handleBtnChange = (id) => {
        this.setState({showingSection: id})
    }

    render() {
        const multibuttons = [{
            id: 1,
            text: 'Scores',
            onClick: () => {this.handleBtnChange(1)},
        }, {
            id: 2,
            text: 'Reviews',
            onClick: () => {this.handleBtnChange(2)},
        }]

        let element = (
            <Scores />
        )
        if(this.state.showingSection === 2) {
            element = (
                <Reviews />
            )
        }

        return (
            <div className={styles.Progress}>
                <div>
                    <h2 className={styles.SectionTitle}>Progress</h2>
                    <MultiButton elements={multibuttons} active={this.state.showingSection} className={styles.Multibutton} />
                    <div className={styles.Container}>
                        {element}
                    </div>
               </div>
           </div>
        )
    }
}

const mapStateToProps = state => {
    return {
        showingProvider: state.userData.showingProvider,
    }
}

const mapDispatchToProps = dispatch => {
    return {
        setUsingProvider: () => dispatch(userDataActions.updateUsingProvider(true)),
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Progress)