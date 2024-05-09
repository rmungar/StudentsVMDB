package ViewModel

import ViewModel.IStudentsVM.Companion.MAXCARACTERES
import Repository.StudentRepository
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

class StudentsViewModelDB: IStudentsVM {



    private val studentRepository = StudentRepository()

    private val _newStudent = mutableStateOf("")
    override val newStudent: State<String> = _newStudent

    private val _students = mutableStateListOf<String>()
    override var students = _students

    private val _showInfoMessage = mutableStateOf(false)
    override val showInfoMessage: State<Boolean> = _showInfoMessage

    private val _infoMessage = mutableStateOf("")
    override val infoMessage: State<String> = _infoMessage

    private val _selectedIndex = mutableStateOf(-1) // -1 significa que no hay selección
    override val indiceSeleccionado: State<Int> = _selectedIndex

    override fun addEstudiante() {
        if (_newStudent.value.isNotBlank()) {
            _students.add(_newStudent.value.trim())
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
        studentRepository.deleteStudent(indiceSeleccionado.value)
    }

    override fun cargarEstudiantes() {
        _students.addAll(studentRepository.getAllStudents().getOrThrow())
    }

    override fun guardarEstudiante() {

        studentRepository.updateStudents(students)

    }

    override fun showInfoMesssage(show: Boolean) {
        _showInfoMessage.value = show
    }

    override fun vaciarEstudiantes() {
        _students.clear()
    }

    override fun estudianteSeleccionado(indice: Int) {
        _selectedIndex.value = indice
    }

}