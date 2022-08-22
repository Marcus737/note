package test;

class RoleBulider{

    Role role;

    public RoleBulider() {
        role = new Role();
    }

    public RoleBulider steId(Integer id){
        role.setId(id);
        return this;
    }

    public RoleBulider steUsername(String roleNmae){
        role.setRoleName(roleNmae);
        return this;
    }

    public Role build(){
        return role;
    }
}

public class Role {

    private String roleName;
    private Integer id;

    public Role() {
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static void main(String[] args) {
        RoleBulider bulider = new RoleBulider();
    }
}
