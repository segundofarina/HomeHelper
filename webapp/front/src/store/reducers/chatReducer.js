import * as actionTypes from '../actions/actionTypes'
import * as apiStatus from '../apiStatus'

const initialState = {
    currentChat: null,
    idCounter: 0,
    status: apiStatus.API_STATUS_NONE,
    chats: [],
    usingAsClient: true,
}

const reducer = (state = initialState, action) => {
    let newMsg = {}
    switch(action.type) {
        case actionTypes.CHAT_INIT_DONE:
            const processChats = processChatsLoaded(action.payload.chats)
            return {
                ...state,
                status: apiStatus.API_STATUS_DONE,
                chats: processChats.chats,
                idCounter: processChats.idCounter,
            }
        case actionTypes.CHAT_INIT_LOADING:
            return {
                ...state,
                status: apiStatus.API_STATUS_LOADING,
            }
        case actionTypes.CHAT_INIT_ERROR:
            return {
                ...state,
                status: apiStatus.API_STATUS_ERROR,
            }
        case actionTypes.CHAT_CURRENT_CHAT_UPDATE:
            return {
                ...state,
                chats: getUpdatedChatListWithoutIsNew(state.chats),
                currentChat: action.payload.chatId
            }
        case actionTypes.CHAT_SEND_MSG:
            newMsg = {
                id: state.idCounter,
                from: 'mine',
                text: action.payload.newMsg,
                isNewMsg: true,
            }
            return {
                ...state,
                chats: getUpdatedChatListByCurrentChat(state.chats, newMsg, state.currentChat),
                idCounter: state.idCounter + 1,
            }
        case actionTypes.CHAT_RECV_MSG:
            newMsg = {
                id: state.idCounter,
                from: 'yours',
                text: action.payload.newMsg,
                isNewMsg: true,
            }
            return {
                ...state,
                chats: getUpdatedChatListByUsername(state.chats, newMsg, action.payload.username),
                idCounter: state.idCounter + 1,
            }
        case actionTypes.CHAT_UPDATE_IS_NEW_MSG:
            return {
                ...state,
                chats: getUpdatedChatListWithoutIsNew(state.chats),
            }
        case actionTypes.CHAT_UPDATE_USING_AS_CLIENT:
            return {
                ...state,
                usingAsClient: action.payload.usingAsClient
            }
        case actionTypes.CHAT_CLEAR_STATE:
            return initialState
        default:
            return state
    }
}

const getUpdatedChatListByCurrentChat = (chats, newMsg, currentChat) => {
    const updatedChats = []
    chats.forEach(chat => {
        if(chat.chatId !== currentChat) {
            updatedChats.push({...chat})
        } else {
            const newMessages = [...chat.messages, {
                ...newMsg,
            }]
            const newChatObj = {
                ...chat,
                messages: newMessages,
            }
            updatedChats.push(newChatObj)
        }
    })

    return updatedChats
}

const getUpdatedChatListByUsername = (chats, newMsg, username) => {
    const updatedChats = []
    chats.forEach(chat => {
        if(chat.toUsername !== username) {
            updatedChats.push({...chat})
        } else {
            const newMessages = [...chat.messages, {
                ...newMsg,
            }]
            const newChatObj = {
                ...chat,
                messages: newMessages,
            }
            updatedChats.push(newChatObj)
        }
    })

    return updatedChats

}

const getUpdatedChatListWithoutIsNew = (chats) => {
    const updatedChats = []
    chats.forEach(chat => {
        const newMessages = chat.messages.map(message => {
            return {
                id: message.id,
                from: message.from,
                text: message.text,
            }
        })
        const newChatObj = {
            ...chat,
            messages: newMessages,
        }
        updatedChats.push(newChatObj)
    })

    return updatedChats
}

const processChatsLoaded = (chats) => {
    const updatedChats = []
    let idCount = 0

    chats.forEach(chat => {
        const newMessages = chat.messages.map(message => {
            let id = idCount
            idCount += 1
            return {
                ...message,
                id: id,
            }
        })

        updatedChats.push({
            ...chat,
            messages: newMessages
        })
    })

    return {
        chats: updatedChats,
        idCounter: idCount,
    }
}

export default reducer