import React, { Component } from 'react'
import styles from './ScoresDetails.module.css'
import Panel from '../../../../UI/Panel/Panel'
import ScoreRow from './ScoreRow/ScoreRow'

class ScoresDetails extends Component {
    state = {
        quality: false,
        price: false,
        punctuality: false,
        treatment: false,
        cleanness: false,
    }

    handleRowClick = (itemName) => {
        this.setState((prevState) => {
            const newState = {...prevState}
            newState[itemName] = !prevState[itemName]
            return newState
        })
    }

    render () {
        return (
            <Panel className={styles.ScoresDetails}>
                <ScoreRow className={styles.ScoreRow}
                            description='Quality'
                            showingDetails={this.state.quality}
                            onClick={() => this.handleRowClick('quality')}
                            details={this.props.details.quality}
                            total={this.props.details.total} />
                <ScoreRow className={styles.ScoreRow}
                            description='Price'
                            showingDetails={this.state.price}
                            onClick={() => this.handleRowClick('price')}
                            details={this.props.details.price}
                            total={this.props.details.total} />
                <ScoreRow className={styles.ScoreRow}
                            description='Punctuality'
                            showingDetails={this.state.punctuality}
                            onClick={() => this.handleRowClick('punctuality')}
                            details={this.props.details.punctuality}
                            total={this.props.details.total}  />
                <ScoreRow className={styles.ScoreRow}
                            description='Treatment'
                            showingDetails={this.state.treatment}
                            onClick={() => this.handleRowClick('treatment')}
                            details={this.props.details.punctuality}
                            total={this.props.details.total}  />
                <ScoreRow className={styles.ScoreRow}
                            description='Cleanness'
                            showingDetails={this.state.cleanness}
                            onClick={() => this.handleRowClick('cleanness')}
                            details={this.props.details.cleanness}
                            total={this.props.details.total}  />
            </Panel>
        )
    }
} 

export default ScoresDetails