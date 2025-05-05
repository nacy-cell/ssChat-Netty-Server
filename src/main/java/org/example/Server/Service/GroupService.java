package org.example.Server.Service;

import java.util.List;
import java.util.Map;

public interface GroupService {
    String createGroup(String groupName, String ownerId, String groupAvatar);
    boolean addGroupMember(String groupId, String memberId, String nickname, int role);
    boolean removeGroupMember(String groupId, String memberId);
    List<Map<String, Object>> getGroupMembers(String groupId);
    List<Map<String, Object>> getUserGroups(String userId);
    boolean isGroupMember(String groupId, String userId);
}