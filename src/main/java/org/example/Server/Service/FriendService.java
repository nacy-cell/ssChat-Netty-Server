package org.example.Server.Service;

import java.util.List;
import java.util.Map;

public interface FriendService {
    boolean addFriendRequest(String userId, String friendId);
    boolean acceptFriendRequest(String userId, String friendId);
    boolean rejectFriendRequest(String userId, String friendId);
    List<Map<String, Object>> getFriendRequests(String userId);
    List<Map<String, Object>> getFriendList(String userId);
}