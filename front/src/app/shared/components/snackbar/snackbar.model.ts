export type MessageType = 'info' | 'error' | 'success' | null
export interface Snackbar {
    show: boolean;
    message: string;
    type: MessageType;
}