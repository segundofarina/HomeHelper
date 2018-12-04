import React from 'react'
import GenericError from '../GenericError/GenericError'

const badRequest = () => (
    <GenericError number='400' description='Bad Request' />
)

export default badRequest