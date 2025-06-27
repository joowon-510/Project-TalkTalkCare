export interface Friend {
  userId: number;
  name: string;
  s3Filename: string;
  phone: string;
  status: 'ONLINE' | 'OFFLINE';
  displayStatus: string;
  lastActiveTime?: string;
}

export interface FriendStatusUpdate {
  userId: number;
  type: 'LOGIN' | 'LOGOUT';
  lastActiveTime: string;
  displayStatus: string;
}

export interface FriendListProps {
  userId: number;
  onClose: () => void;
  wsUrl?: string;
  apiUrl?: string;
}

export interface FriendItemProps {
  friend: Friend;
  onRemove?: () => void;
}

export interface AddFriendRequest {
  userId: number;
  name: string;
  phone: string;
}