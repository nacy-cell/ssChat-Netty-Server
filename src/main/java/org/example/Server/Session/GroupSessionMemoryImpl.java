package org.example.Server.Session;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import io.netty.channel.Channel;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.Server.Dao.GroupMapper;

public class GroupSessionMemoryImpl implements GroupSession {
    private final Map<String, Group> groupMap = new ConcurrentHashMap<>();
    private SqlSessionFactory sqlSessionFactory;

    public GroupSessionMemoryImpl() {
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public int createGroup(Integer creator,String name,Set<Integer> members) {
        try (SqlSession session = sqlSessionFactory.openSession()){
            GroupMapper groupMapper = session.getMapper(GroupMapper.class);
            int t = groupMapper.createGroup(name,creator);
            if (t != 0){
                for (Integer i : members){
                    joinMember(name,i);
                }
                return t;
            }else{
                return 0;
            }
        }
    }

    @Override
    public int joinMember(String name, Integer member) {
        try (SqlSession session = sqlSessionFactory.openSession()){
            GroupMapper groupMapper = session.getMapper(GroupMapper.class);
            return groupMapper.joinMember(name,member);
        }
    }

    @Override
    public int removeMember(String name, Integer member) {
        try (SqlSession session = sqlSessionFactory.openSession()){
            GroupMapper groupMapper = session.getMapper(GroupMapper.class);
            return groupMapper.removeMember(name,member);
        }
    }

    @Override
    public int removeGroup(String name) {
        try (SqlSession session = sqlSessionFactory.openSession()){
            GroupMapper groupMapper = session.getMapper(GroupMapper.class);
            return groupMapper.removeGroup(name);
        }
    }

    @Override
    public Set<Integer> getMembers(String name) {
        try (SqlSession session = sqlSessionFactory.openSession()){
            GroupMapper groupMapper = session.getMapper(GroupMapper.class);
            return groupMapper.getMembers(name);
        }
    }

    // 根据 【群聊名称】 -> 【用户名Set】 -> map遍历 -> 【用户名获取到 所有对应的 channel】 -> 【channel List】
    @Override
    public List<Channel> getMembersChannel(String name) {
        return getMembers(name).stream()
                .map(member -> SessionFactory.getSession().getChannel(member)) // 根据成员名 获得Channel
                .filter(Objects::nonNull)                                      // 不是 null 才会 被下面收集
                .collect(Collectors.toList());
    }
}
