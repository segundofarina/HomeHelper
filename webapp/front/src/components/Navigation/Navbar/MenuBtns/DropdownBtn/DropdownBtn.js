import React, { Component } from 'react'
import styles from './DropdownBtn.module.css'
import DropdownItemBtn from './DropdownItemBtn/DropdownItemBtn'
import defaultProfileImage from '../../../../../assets/img/defaultProfile.png'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faChevronDown } from '@fortawesome/free-solid-svg-icons'

class DropdownBtn extends Component {
    state = {
        open: false,
    }

    toggleOpen = () => {
        this.setState((prevState) => ({open: !prevState.open}))
    }

    closeList = () => {
        this.setState({open: false})
    }

    setWrapperRef = (node) => {
        this.wrapperRef = node
    }

    handleClickOutside = (event) => {
        if(this.wrapperRef && !this.wrapperRef.contains(event.target)) {
            this.closeList()
        }
    }

    componentDidMount() {
        document.addEventListener('mousedown', this.handleClickOutside)
    }

    componentWillUnmount() {
        document.removeEventListener('mousedown', this.handleClickOutside)
    }
   
    render() {
        let profileImage = this.props.img
        if(!profileImage) {
            profileImage = defaultProfileImage
        }

        const listStylesArr = [styles.dropdownList]
        if(this.state.open) {
            listStylesArr.push(styles.open)
        }

        const headerStylesArr = [styles.dropdownHeader]
        if(this.state.open) {
            headerStylesArr.push(styles.open)
        }

        let firstItem = (
            <DropdownItemBtn to="/createProvider"
                            onClick={this.closeList}>
                Crear perfil del proveedor
            </DropdownItemBtn>
        )
        if(this.props.isProvider) {
            firstItem = (
                <DropdownItemBtn to="/provider"
                                onClick={() => {
                                    this.closeList()
                                    this.props.handleUseAsProvider(true)
                                }}>
                    Usar como proveedor
                </DropdownItemBtn>
            )
        }
        if(this.props.showingProvider) {
            firstItem = (
                <DropdownItemBtn to="/"
                                onClick={() => {
                                    this.closeList()
                                    this.props.handleUseAsProvider(false)
                                }}>
                    Usar como cliente
                </DropdownItemBtn>
            )
        }

        return (
            <div ref={this.setWrapperRef}>
                <div className={headerStylesArr.join(' ')}
                    onClick={this.toggleOpen}>
                    <img src={this.props.img} 
                    onError={(ev)=>ev.target.src = defaultProfileImage} 
                    className={styles.ProfilePicture}/>
                    <span>{this.props.name}</span>
                    <FontAwesomeIcon icon={faChevronDown} className={styles.dropdownIcon} />
                </div>
                <div className={listStylesArr.join(' ')}>
                    {firstItem}
                    <DropdownItemBtn icon="logout"
                                    to="/"
                                    onClick={() => {
                                        this.closeList()
                                        this.props.handleLogOut()
                                    }}>
                        Cerrar Sesion
                    </DropdownItemBtn>
                </div>
            </div>
        )
    }
}

export default DropdownBtn