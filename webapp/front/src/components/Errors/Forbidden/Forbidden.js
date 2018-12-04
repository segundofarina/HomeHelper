import React from 'react'
import GenericError from '../GenericError/GenericError'

const forbidden = () => (
    <GenericError number='403' description='Forbidden' />
)

export default forbidden