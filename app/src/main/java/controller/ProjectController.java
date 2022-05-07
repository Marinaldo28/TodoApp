
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Project;
import util.ConnectionFactory;

public class ProjectController {
    
    public void save(Project project){
        String sql = "INSERT INTO projects (name, description, "
                + "createdAt, updateAt) VALUES "
                + "(?, ?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, project.getName());
            ps.setString(2, project.getDescription());
            ps.setDate(3, new java.sql.Date(project.getCreatedAt().getTime()));
            ps.setDate(4, new java.sql.Date(project.getUpdatedAt().getTime()));
            ps.execute();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inserir o projeto");
        } finally{
            ConnectionFactory.closeConnection(conn, ps);
        }
    }
    
    public void update(Project project){
        String sql = "UPDATE projects SET name = ?, description = ? "
                + "createdAt = ?, updatedAt = ? WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, project.getName());
            ps.setString(2, project.getDescription());
            ps.setDate(3, new java.sql.Date(project.getCreatedAt().getTime()));
            ps.setDate(4, new java.sql.Date(project.getUpdatedAt().getTime()));
            ps.setInt(5, project.getId());
            ps.execute();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar o projeto");
        } finally{
            ConnectionFactory.closeConnection(conn, ps);
        }
    }
    
    public void removeById(int projectId){
        String sql = "DELETE FROM projects WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, projectId);
            ps.execute();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar o projeto");
        } finally{
            ConnectionFactory.closeConnection(conn, ps);
        }
    }
    
    public List<Project> getAll (){
        String sql = "SELECT * FROM projects";
        
        Connection conn = null;
        PreparedStatement ps= null;
        ResultSet rs = null;
        
        List<Project> listProjects = new ArrayList<>();
        
        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
                Project project = new Project();
                project.setId(rs.getInt("id"));
                project.setName(rs.getString("name"));
                project.setDescription(rs.getString("description"));
                project.setCreatedAt(rs.getDate("createdAt"));
                project.setUpdatedAt(rs.getDate("updatedAt"));
                
                listProjects.add(project);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar os projetos");
        } finally{
            ConnectionFactory.closeConnection(conn, ps, rs);
        }
        return listProjects;
    }
}
