import React, { Component } from 'react'
import { connect } from 'react-redux'
import styles from './Chat.module.css'
import ContactProfile from './ContactProfile/ContactProfile'
import MessagesBox from './MessagesBox/MessagesBox'
import MsgInput from './MsgInput/MsgInput'
import * as chatActions from '../../../store/actions/chatActions'

class Chat extends Component {
    state = {
        newMsg: '',
    }

    msgInputRef = React.createRef()
    msgBoxRef = React.createRef()

    componentDidMount () {
        this.msgInputRef.current.focus()
        this.scrollMsgBoxToBottom()
    }

    msgChangeHandler = (event) => {
        this.setState({newMsg: event.target.value})
    }

    scrollMsgBoxToBottom = () => {
        this.msgBoxRef.current.scrollTop = this.msgBoxRef.current.scrollHeight - 150
    }

    sendBtnHandler = () => {
        if(this.state.newMsg !== '') {
            this.scrollMsgBoxToBottom()

            /* Send msg to websocket */
            const websocketMsg = {
                username: this.props.username,
                text: this.state.newMsg,
            }
            this.props.websocketSendHandler(websocketMsg)

            this.props.chatSendMsg(this.state.newMsg)

            /* Keep this to clean the input */
            this.setState({newMsg: ''})
        }
    }

    render () {
        return (
            <div className={styles.Chat}>
                <ContactProfile 
                name={this.props.contactName}
                img={this.props.img} />
                <MessagesBox messages={this.props.chatMessages} ref={this.msgBoxRef} />
                <MsgInput msgValue={this.state.newMsg}
                    msgChangeHandler={this.msgChangeHandler}
                    sendBtnHandler={this.sendBtnHandler}
                    ref={this.msgInputRef}
                />
            </div>
        )
    }
}

const mapDispatchToProps = dispatch => {
    return {
        chatSendMsg: (msg) => dispatch(chatActions.chatSendMsg(msg))
    }
}

export default connect(null, mapDispatchToProps)(Chat)