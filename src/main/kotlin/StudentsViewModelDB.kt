import IStudentsVM.Companion.MAXCARACTERES
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList

class StudentsViewModelDB:IStudentsVM {



    private val studentRepository = StudentRepository()

    private val _newStudent = mutableStateOf("")
    override val newStudent: State<String> = _newStudent

    private val _students = mutableStateListOf<Pair<Int,String>>()
    override var students = _students

    private val _showInfoMessage = mutableStateOf(false)
    override val showInfoMessage: State<Boolean> = _showInfoMessage

    private val _infoMessage = mutableStateOf("")
    override val infoMessage: State<String> = _infoMessage

    private val _selectedIndex = mutableStateOf(-1) // -1 significa que no hay selección
    override val indiceSeleccionado: State<Int> = _selectedIndex

    override fun addEstudiante() {
        if (_newStudent.value.isNotBlank()) {
            _students.add(Pair(contadorIDS(),_newStudent.value.trim()))
            _newStudent.value = ""
        }
    }

    override fun newStudentChange(name:String) {
        if (name.length <= MAXCARACTERES) {
            _newStudent.value = name
        }
    }

    override fun borrarEstudiante(indice: Int){
        val estudiante = _students[indice]
        studentRepository.deleteStudent(estudiante.first)
    }

    override fun cargarEstudiantes() {
        _students.addAll(studentRepository.getAllStudents().getOrThrow())
    }

    override fun guardarEstudiante() {
        val lista = mutableListOf("")
        students.forEach {
            lista.add(it.second)
        }
        studentRepository.updateStudents(lista)
    }

    override fun showInfoMesssage(show: Boolean) {
        _showInfoMessage.value = show
    }

    override fun vaciarEstudiantes() {
        students.clear()
    }

    override fun estudianteSeleccionado(indice: Int) {
        _selectedIndex.value = indice
    }

    private fun contadorIDS(): Int{
        if (_students.size > 0){
            var cont = _students[_students.size - 1].first
            cont ++
            return cont
        }
        else{
            var cont = 0
            cont ++
            return cont
        }
    }
}