
import UI.VentanaPrincipal
import ViewModel.IStudentsVM
import ViewModel.StudentsViewModelDB
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application{
    val studentsViewModel : IStudentsVM = StudentsViewModelDB()
    studentsViewModel.cargarEstudiantes()

    Window(
        visible = true ,
        onCloseRequest = ::exitApplication
    ){
        VentanaPrincipal()
    }

}
