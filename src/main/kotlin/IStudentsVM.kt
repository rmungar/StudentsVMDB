import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList

interface IStudentsVM {

    companion object{
        const val MAXCARACTERES = 10
        const val MAXIMOSVISIBLES = 7
    }


    fun newStudentChange(name: String)

    val newStudent: State<String>

    val students: SnapshotStateList<String>

    val showInfoMessage: State<Boolean>

    val infoMessage: State<String>

    val indiceSeleccionado: State<Int>

    fun addEstudiante()

    fun borrarEstudiante(indice: Int)

    fun cargarEstudiantes()

    fun guardarEstudiante()

    fun showInfoMesssage(show: Boolean)

    fun vaciarEstudiantes()

    fun estudianteSeleccionado(indice: Int)

}