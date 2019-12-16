import React, { Component, Fragment } from 'react'
import styles from './Messages.module.css'
import SockJsClient from 'react-stomp'
import { connect } from 'react-redux'
import ContactList from '../../../components/Messages/ContactList/ContactList'
import Chat from '../../../components/Messages/Chat/Chat'
import Status from '../../../components/Messages/Status/Status'
import SelectChatMsg from '../../../components/Messages/SelectChatMsg/SelectChatMsg'
import * as chatActions from '../../../store/actions/chatActions'
import * as apiStatus from '../../../store/apiStatus'
import * as userDataActions from '../../../store/actions/userDataActions'

class Messages extends Component {

    url = "http://pawserver.it.itba.edu.ar/paw-2018a-4/ws/websocket"
    //url = "http://localhost:8080/ws/websocket"
    /*TODO profiles*/

    state = {
        socketConnected: true,
    }

    websocketRef = React.createRef()

    componentWillMount() {
        if(!this.props.showingProvider) {
            this.props.setUsingProvider()
        }
    }

    componentDidMount() {
        if(this.props.status === apiStatus.API_STATUS_NONE || this.props.usingAsClient) {
            this.props.chatUseAsProvider()
            this.props.chatInit()
        }
    }

    handleContactClick = (chatId) => {
        this.props.currentChatUpdate(chatId)
    }

    handleRecvMsg = (msg) => {
        console.log('recv: ')
        console.log(msg)
        if(!msg.usingAsClient) {
            this.props.chatRecvMsg(msg.username, msg.text)
        }
    }

    handleSendMsg = (msg) => {
        const providerMsg = {
            ...msg,
            usingAsClient: false,
        }
        this.websocketRef.current.sendMessage('/app/messages', JSON.stringify(providerMsg), {'X-Authorization' : this.props.token})
    }

    handleSocketConnect = () => {
        if(!this.state.socketConnected) {
            this.setState({socketConnected: true})
        }
    }

    handleSocketDisconnect = () => {
        if(this.state.socketConnected) {
            this.setState({socketConnected: false})
            this.props.chatUpdateIsNewMsg()
        }
    }

    reconnectHandler = () => {
        this.props.chatInit()
    }

    render () {
        if(this.props.usingAsClient) {
            return (
                <Status text='Loading...' type='loading' />
            )
        }
        
        const currentChat = this.props.chats.filter((chat) => {
            return chat.chatId === this.props.currentChat
        })
        let currentChatMessages = []
        let currentContactName = ''
        let currentToUsername = ''
        let currentImg= ''
        if(currentChat[0]) {
            currentChatMessages = currentChat[0].messages
            currentContactName = currentChat[0].toName
            currentToUsername = currentChat[0].toUsername
            currentImg = currentChat[0].toPictureUrl
        }

        const contacts = this.props.chats.map(chat => {
            const msg = chat.messages.length > 0 ? chat.messages[chat.messages.length - 1].text : ''
            return {
                id: chat.chatId,
                name: chat.toName,
                msg: msg,
                img: chat.toPictureUrl,
            }
        })
        console.log(this.props.chats)
        let sideElem = (<SelectChatMsg />)
        if(this.props.currentChat !== null) {
            sideElem = (
                <Chat websocketSendHandler={this.handleSendMsg}
                        chatMessages={currentChatMessages}
                        username={currentToUsername}
                        contactName={currentContactName}
                        img={currentImg} />
            )
        }

        let element = (<Status text='Loading...' type='loading' />)
        if(this.props.status === apiStatus.API_STATUS_ERROR) {
            element = (<Status text='Error while connecting to the server' type='error' reconnectHandler={this.reconnectHandler} />)
        }
        if(this.props.status === apiStatus.API_STATUS_DONE) {
            if(this.props.chats.length === 0) {
                element = (<Status text="You don't have any messages yet..." type='empty' />)
            } else if(!this.state.socketConnected) {
                element = (<Status text='Lost connection to the server. Trying to reconnect...' type='loading' />)
            } else {
                element = (
                    <Fragment>
                        <SockJsClient url={this.url} 
                                    ref={this.websocketRef} 
                                    topics={["/user/queue/messages"]} 
                                    onMessage={this.handleRecvMsg} 
                                    headers={{ "X-Authorization": this.props.token }} 
                                    subscribeHeaders={{ "X-Authorization": this.props.token}}
                                    autoReconnect
                                    onConnect={this.handleSocketConnect}
                                    onDisconnect={this.handleSocketDisconnect} />
                        <ContactList contacts={contacts} 
                                    handleContactClick={this.handleContactClick} 
                                    activeChat={this.props.currentChat} />
                        {sideElem}
                   </Fragment>
                )
            }
        }

        return (
            <div className={styles.Container}>
                {element}
            </div>
        )
    }
}

const mapStateToProps = state => {
    return {
        currentChat: state.chat.currentChat,
        chats: state.chat.chats,
        status: state.chat.status,
        usingAsClient: state.chat.usingAsClient,
        token: state.userData.userData.token,
        showingProvider: state.userData.showingProvider,
    }
}

const mapDispatchToProps = dispatch => {
    return {
        currentChatUpdate: (chatId) => dispatch(chatActions.currentChatUpdate(chatId)),
        chatRecvMsg: (username, msg) => dispatch(chatActions.chatRecvMsg(username, msg)),
        chatInit: () => dispatch(chatActions.chatInit({
            usingAsClient: false,
        })),
        chatUpdateIsNewMsg: () => dispatch(chatActions.chatUpdateIsNewMsg()),
        chatUseAsProvider: () => dispatch(chatActions.chatUpdateUsingAsClient(false)),
        setUsingProvider: () => dispatch(userDataActions.updateUsingProvider(true)),
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Messages)
