
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Project;
import model.Task;
import util.ConnectionFactory;

public class TaskController {
    
    public void save(Task task){
        String sql = "INSERT INTO tasks (idProject, name, description, "
                + "completed, notes, deadline, createdAt, updateAt) VALUES "
                + "(?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, task.getIdProject());
            ps.setString(2, task.getName());
            ps.setString(3, task.getDescription());
            ps.setBoolean(4, task.getCompleted());
            ps.setString(5, task.getNotes());
            ps.setDate(6, new java.sql.Date(task.getDeadline().getTime()));
            ps.setDate(7, new java.sql.Date(task.getCreatedAt().getTime()));
            ps.setDate(8, new java.sql.Date(task.getUpdatedAt().getTime()));
            ps.execute();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inserir a tarefa");
        } finally{
            ConnectionFactory.closeConnection(conn, ps);
        }
    }
    
    public void update(Task task){
        String sql = "UPDATE tasks SET idProject = ?, name = ?, "
                + "description = ?, completed = ?, notes = ?, deadline = ?, "
                + "createdAt = ?, updatedAt = ? WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, task.getIdProject());
            ps.setString(2, task.getName());
            ps.setString(3, task.getDescription());
            ps.setBoolean(4, task.getCompleted());
            ps.setString(5, task.getNotes());
            ps.setDate(6, new java.sql.Date(task.getDeadline().getTime()));
            ps.setDate(7, new java.sql.Date(task.getCreatedAt().getTime()));
            ps.setDate(8, new java.sql.Date(task.getUpdatedAt().getTime()));
            ps.setInt(9, task.getId());
            ps.execute();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar a tarefa");
        } finally{
            ConnectionFactory.closeConnection(conn, ps);
        }
    }
    
    public void removeById(int taskId){
        String sql = "DELETE FROM tasks WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, taskId);
            ps.execute();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar a tarefa");
        } finally{
            ConnectionFactory.closeConnection(conn, ps);
        }
    }
    
    public List<Task> getAll (Integer idProject){
        String sql = "SELECT * FROM tasks WHERE idProject = ?";
        
        Connection conn = null;
        PreparedStatement ps= null;
        ResultSet rs = null;
        
        List<Task> listTasks = new ArrayList<>();
        
        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idProject);
            rs = ps.executeQuery();
            
            while(rs.next()){
                Task task = new Task();
                task.setId(rs.getInt("id"));
                task.setIdProject(rs.getInt("idProject"));
                task.setName(rs.getString("name"));
                task.setDescription(rs.getString("description"));
                task.setCompleted(rs.getBoolean("completed"));
                task.setNotes(rs.getString("notes"));
                task.setDeadline(rs.getDate("deadline"));
                task.setCreatedAt(rs.getDate("createdAt"));
                task.setUpdatedAt(rs.getDate("updatedAt"));
                
                listTasks.add(task);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar as tarefas");
        } finally{
            ConnectionFactory.closeConnection(conn, ps, rs);
        }
        return listTasks;
    }
}
