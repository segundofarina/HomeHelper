import React from 'react'
import styles from './Scores.module.css'
import ProfileDetails from './ProfileDetails/ProfileDetails'
import ScoresDetails from './ScoresDetails/ScoresDetails'

const scores = () => {

    const details = {
        quality: {
            _1: 0,
            _2: 0,
            _3: 0,
            _4: 3,
            _5: 10,
        },
        price: {
            _1: 1,
            _2: 0,
            _3: 0,
            _4: 3,
            _5: 9,
        },
        punctuality: {
            _1: 0,
            _2: 4,
            _3: 0,
            _4: 3,
            _5: 3,
        },
        treatment: {
            _1: 0,
            _2: 0,
            _3: 10,
            _4: 3,
            _5: 0,
        },
        cleanness: {
            _1: 3,
            _2: 2,
            _3: 2,
            _4: 4,
            _5: 2,
        },
        total: 13,
   }

   const generalCalification = 4.3

    return (
        <div className={styles.ScoresContainer}>
            <div className={styles.ProfileInfo}>
                <ProfileDetails name='Martin Victory'
                                generalCalification={generalCalification}
                                img=""/>
            </div>
            <div className={styles.ScoresInfo}>
                <ScoresDetails details={details} />
            </div>

        </div>
    )
}

export default scores