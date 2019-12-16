import * as actionTypes from './actionTypes'
import axios from 'axios'

export const currentChatUpdate = (chatId) => {
    return {
        type: actionTypes.CHAT_CURRENT_CHAT_UPDATE,
        payload: {
            chatId: chatId,
        }
    }
}

export const chatSendMsg = (msg) => {
    return {
        type: actionTypes.CHAT_SEND_MSG,
        payload: {
            newMsg: msg,
        }
    }
}

export const chatRecvMsg = (username, msg) => {
    return {
        type: actionTypes.CHAT_RECV_MSG,
        payload: {
            username: username,
            newMsg: msg,
        }
    }
}

export const loadingChats = () => {
    return {
        type: actionTypes.CHAT_INIT_LOADING,
    }
}

export const errorChats = () => {
    return {
        type: actionTypes.CHAT_INIT_ERROR,
    }
}

export const saveChats = (chats) => {
    return  {
        type: actionTypes.CHAT_INIT_DONE,
        payload: {
            chats: chats,
        },
    }
}

const chatInitDefault = {
    usingAsClient: true,
}

export const chatInit = (args = chatInitDefault) => {
    return async dispatch => {
        dispatch(loadingChats())
        dispatch(currentChatUpdate(null))

        let path = '/users/messages'
        if(!args.usingAsClient) {
            path = '/providers/messages'
        }

        /* Async fetch the api */
        try {
            const resp = await axios.get(path)
            const chats = resp.data.chats
            dispatch(saveChats(chats))
        } catch(error) {
            dispatch(errorChats())
            console.log(error)
        }
    }
}

export const chatUpdateIsNewMsg = () => {
    return {
        type: actionTypes.CHAT_UPDATE_IS_NEW_MSG,
    }
}

export const chatUpdateUsingAsClient = (useAsClient) => {
    return {
        type: actionTypes.CHAT_UPDATE_USING_AS_CLIENT,
        payload: {
            usingAsClient: useAsClient,
        }
    }
}

export const chatClearState = () => {
    return {
        type: actionTypes.CHAT_CLEAR_STATE,
    }
}