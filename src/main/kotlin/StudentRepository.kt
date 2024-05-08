import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement

class StudentRepository(){

// NO HAY QUE TOCAR NADA, YA FUNCIONA SIN EL .USE
    fun getAllStudents(): Result<List<Pair<Int,String>>> {

        var connectionDb: Connection? = null
        var stmt : Statement? = null
        try {
            connectionDb = Database.getConnection()
            stmt = connectionDb.createStatement()
            val students = mutableListOf<Pair<Int, String>>()

            val query = "SELECT id, name FROM students"
            val rs = stmt.executeQuery(query)

            while (rs.next()){
                students.add(Pair(rs.getString("id").toInt(),rs.getString("name")))
            }
            stmt.close()
            connectionDb.close()
            return Result.success(students)

        } catch (e: Exception) {
            stmt?.close()
            connectionDb?.close()
            return Result.failure(e)
        }
    }

    fun updateStudents(students: List<String>): Result<Unit> {

        var connectionDb : Connection? = null
        var stmt: Statement? = null
        var error: Exception? = null

        try {
            connectionDb = Database.getConnection()
            connectionDb.autoCommit = false
            connectionDb.createStatement()
            stmt = connectionDb.createStatement()
            val query = "DELETE FROM students"
            stmt.executeQuery(query)

            stmt = connectionDb.prepareStatement("INSERT INTO students (name) VALUES (?)")
            for (student in students) {
                stmt.setString(1, student)
                stmt.executeUpdate()
            }

            connectionDb.commit()
        } catch (e: Exception) {
            error = e
           try {
               connectionDb?.rollback()
           }
           catch (e:SQLException){
               error = e
           }
        } finally {
            connectionDb?.autoCommit = true
            stmt?.close()
            connectionDb?.close()
        }

        if (error != null){
            return Result.failure(error)
        }

        return Result.success(Unit)
    }


    fun deleteStudent(studentId: Int): Result<Unit>{
        var connectionDb: Connection? = null
        var statement: Statement? = null
        try {
            connectionDb = Database.getConnection()
            connectionDb.autoCommit = false
            statement = connectionDb.prepareStatement("DELETE FROM students WHERE id = (?)")
            statement.setString(1, studentId.toString())
            statement.executeQuery()
            connectionDb.autoCommit = true
            return Result.success(Unit)
        }
        catch (e:Exception){
            connectionDb?.autoCommit = true
            connectionDb?.close()
            statement?.close()
            return Result.failure(e)
        }
        finally {
            connectionDb?.close()
            statement?.close()
        }
    }

}