package com.example.newstudysmart.presentation.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.newstudysmart.R
import com.example.newstudysmart.presentation.Domain.Model.Session
import com.example.newstudysmart.presentation.Domain.Model.Subject
import com.example.newstudysmart.presentation.Domain.Model.Task
import com.example.newstudysmart.presentation.components.AddSubjectDialog
import com.example.newstudysmart.presentation.components.CountCard
import com.example.newstudysmart.presentation.components.DeleteDialogBox
import com.example.newstudysmart.presentation.components.StudySessionsList
import com.example.newstudysmart.presentation.components.SubjectCard
import com.example.newstudysmart.presentation.components.taskList
import com.example.newstudysmart.presentation.destinations.SessionScreenRouteDestination
import com.example.newstudysmart.presentation.destinations.SubjectScreenRouteDestination
import com.example.newstudysmart.presentation.destinations.TaskScreenRouteDestination
import com.example.newstudysmart.presentation.subject.SubjectScreenNavArgs
import com.example.newstudysmart.presentation.task.TaskScreenNavArgs
import com.example.newstudysmart.util.SnackbarEvent
import com.example.studysmart.presentation.dashboard.DashboardEvent
import com.example.studysmart.presentation.dashboard.DashboardState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@RootNavGraph(start = true)
@Destination
@Composable
fun DashBoardScreenRoute(
    navigator : DestinationsNavigator
){
    val viewModel : DashBoardViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val tasks by viewModel.tasks.collectAsStateWithLifecycle()
    val recentSessions by viewModel.recentSessions.collectAsStateWithLifecycle()


   DashBoardScreen(
       state = state ,
       tasks = tasks ,
       recentSessions = recentSessions,
       onEvent = viewModel:: onEvent,
       snackbarEvent = viewModel.snackEventFlow,
       onSubjectCardClick ={ subjectId->
             subjectId?.let{
                 val navArg = SubjectScreenNavArgs(subjectId = subjectId)
                 navigator.navigate(SubjectScreenRouteDestination(navArgs = navArg))
             }
       },
       onTaskCardClick = { taskId->
           val navArg = TaskScreenNavArgs(subjectId = null , taskId = taskId)
           navigator.navigate(TaskScreenRouteDestination(navArgs = navArg))

       } ,
       onStartSessionButtonClick = {
           navigator.navigate(SessionScreenRouteDestination())
       }
   )
}


@Composable

private fun DashBoardScreen(
    state : DashboardState,
    tasks : List<Task>,
    recentSessions: List<Session>,
    onEvent:(DashboardEvent)  -> Unit,
    snackbarEvent: SharedFlow<SnackbarEvent>,
    onSubjectCardClick : (Int?) -> Unit,
    onTaskCardClick: (Int?) -> Unit,
    onStartSessionButtonClick : () -> Unit

){
//    The Scaffold composable provides a straightforward API you can use to quickly assemble your app's structure according to Material Design guidelines. Scaffold accepts several composables as parameters. Among these are the following:
//
//    topBar: The app bar across the top of the screen.
//    bottomBar: The app bar across the bottom of the screen.
//
    //    floatingActionButton: A button that hovers over the bottom-right corner of the screen that you can use to expose key actions.

    var isAddSubjectDialogOpen by rememberSaveable { mutableStateOf(false) }
    var isDeleteSessionDialogOpen by rememberSaveable { mutableStateOf(false) }
    
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    
    LaunchedEffect(key1 = true ){
        snackbarEvent.collectLatest {
            event->
            when(event){
                is SnackbarEvent.ShowSnackbar->{
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = event.duration
                    )
                }

                SnackbarEvent.NavigateUp -> {}
            }
        }
    }


    AddSubjectDialog(
        isOpen = isAddSubjectDialogOpen  ,
        subjectName = state.subjectName,
        goalHours = state.goalStudyHours,
        onSubjectNameChange = { onEvent(DashboardEvent.OnSubjectNameChange(it)) } ,
        onGoalHoursChange = { onEvent(DashboardEvent.OnGoalStudyHoursChange(it)) } ,
        selectedColors = state.subjectCardColors,
        onColorChange = { onEvent(DashboardEvent.OnSubjectCardColorChange(it)) },
        onDismissRequest = { isAddSubjectDialogOpen = false } ,
        onConfirmButtonClick = {
            onEvent(DashboardEvent.SaveSubject)
           isAddSubjectDialogOpen = false
        }
    )

    DeleteDialogBox(
        isOpen = isDeleteSessionDialogOpen,
        title = "Delete Session ?",
        bodyText = "Are you sure , you want to delete this session? Your studied hours will be reduced" +
                   "by this session time. This action can be undone" ,
        onDismissRequest = { isDeleteSessionDialogOpen = false } ,
        onConfirmButtonClick = {
            onEvent(DashboardEvent.DeleteSession)
            isDeleteSessionDialogOpen = false
        }
    )
    Scaffold (
        snackbarHost = { SnackbarHost (hostState = snackbarHostState) } ,
        topBar = { DashBoardScreenTopBar() }
    ){paddingValues ->  
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ){
         item {
            CountCardsSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                subjectCount = state.totalSubjectCount, studiedHours = state.totalStudiedHours.toString() , goalHours = state.totalGoalStudyHours.toString()
            )}
         item{
             SubjectCardsSection(
                 modifier = Modifier.fillMaxWidth(),
                 subjectList = state.subjects,
                 onAddIconClicked = {
                     isAddSubjectDialogOpen = true
                 },
                 onSubjectCardClick = onSubjectCardClick

             )
           }
            item {
                Button(
                    onClick = onStartSessionButtonClick,
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(horizontal = 48.dp, vertical = 20.dp)
                ){
                    Text(text = "Start Study Session ")
                }

            }
            taskList(
                sectionTitle = "UPCOMING TASKS" ,
                emptyListText =  "You don't have any upcoming tasks . \n "  +
                             "Click the + button in subject screen to add new task",
                tasks = tasks ,
                onCheckBoxClick = { onEvent(DashboardEvent.OnTaskIsCompleteChange(it)) } ,
                onTaskCardClick = onTaskCardClick

            )
            item{
                Spacer(modifier = Modifier.height(20.dp))
            }
            StudySessionsList(
                sectionTitle = "RECENT STUDY SESSIONS" ,
                emptyListText =  "You don't have any recent Study sessions. \n "  +
                        "Start a study session to begin recording your progress",
               sessions = recentSessions ,
                onDeleteClick = {
                    onEvent(DashboardEvent.OnDeleteSessionButtonClick(it))
                    isDeleteSessionDialogOpen = true }
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashBoardScreenTopBar(){
    CenterAlignedTopAppBar(
        title = { 
            Text(text = "Study Smart",
                style = MaterialTheme.typography.headlineMedium)
        })
}

@Composable
private fun CountCardsSection(
    modifier: Modifier ,
    subjectCount: Int ,
    studiedHours  : String ,
    goalHours :String
){
    Row(modifier = modifier){
        CountCard(
            modifier = Modifier.weight(1f) ,
            headingText = "Subject Count", 
            count = "$subjectCount"
        )
        Spacer(modifier = Modifier.width(10.dp))
        CountCard(
            modifier = Modifier.weight(1f) ,
            headingText = "Studied Hours",
            count = studiedHours
        )
        Spacer(modifier = Modifier.width(10.dp))
        CountCard(
            modifier = Modifier.weight(1f) ,
            headingText = "Goal Study Hours",
            count = goalHours
            )

    }
}

@Composable
private fun SubjectCardsSection(
    modifier: Modifier ,
    subjectList : List<Subject> ,
    emptyListText : String = "You don't have any subjects. \n Click the + button to add new subject." ,
    onAddIconClicked : () -> Unit ,
    onSubjectCardClick: (Int?) -> Unit
) {
    Column {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "SUBJECTS",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 12.dp)
            )
            IconButton(onClick = onAddIconClicked) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Subject"
                )
            }
        }
        if (subjectList.isEmpty()) {
            Image(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally),
                painter = painterResource(R.drawable.img_books),
                contentDescription = emptyListText
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = emptyListText,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp) ,
            contentPadding = PaddingValues(start = 12.dp , end = 12.dp)
        ) {
            items(subjectList){subject->
                SubjectCard(
                    subjectName = subject.name ,
                    gradientColors = subject.colors.map{
                                   Color(it)
                    } ,
                    onClick = {onSubjectCardClick(subject.subjectId)} ,
                )
            }

        }

    }
}
