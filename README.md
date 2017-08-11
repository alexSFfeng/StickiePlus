# Stickie+
---
A personal career planner for increasing productivity and developing a proactive habit.

Use for:
- Organizing daily tasks
- Planning a future plan
- Memo for note taking
- Display to show tasks due on TODAY

## Functionalities
---
The below image shows the general user interface and description of parts of the UI


<img src="https://raw.githubusercontent.com/alexSFfeng/StickiePlus/develop/readMeImages/DemoWithDescriptions.png" width="800">

### General
---
Today's Goals panel and Future Goals/Tasks panel contains goal boxes

**Goals boxes contains the following functionality: **

To change priority of one task, simply **check the checkbox next to a task, a slider for setting priority level will appear.**  To edit the specification (text) of a goal box, simply click on it to edit it. 

**Due date picking functionality is only available for the main panel(future goal/task), today's goals panel is only for displaying tasks due today*

**Note**:
There are 10 levels and when you go beyond or below certain levels, the task box will change color to signify the importance of this task.

 (**GREEN = NOT AS IMPORTANT (CAN DO LAST), YELLOW = DEFAULT (DONE BEFORE GREEN TASKS), RED = IMPORTANT (DO ASAP)**)
 
 Daily task panel contains task boxes
 
 **Daily task boxes contain the following functionality:**
 Each box has a small text field for describing the tasks set for a certain time. They could also be added or removed. Sorting would list them base on which tasks comes first in the day.

### Today's Goals Panel
---
Display all the tasks that are due today; this panel is in sync with the "Future Goals/Tasks" Panel. 

It doesn't have add or move functionality; it mainly serves as a display panel for all the goals/tasks due today. However user can still select the tasks and sort base on on each task's priority. Editing description and priority level is also allowed in this panel.

There is no date picking functionality. Changing the deadline of a corresponding task box in the "Future goal/task" panel would remove that box from this display panel. Likewise, adding a task that is due "TODAY" in the "Future goal/task panel" would result in panel updating to display that added task as well.

### Future Goals and Tasks Panel
---
This panel not only display **ALL** the tasks in the future/past; it also allows adding/removing of tasks. All the task/goal boxes in this panel would be display in **short descriptive form** on the calendar. This panel could also sort base on high priority or low priority. 

#### Date picking functionality
User can edit the deadline of a goal box either by clicking on the calendar icon attached to the box or typing a date into the corresponding task box. Only valid dates would be accepted. After updating the date, the calendar and Today's Goals panel would update as well.


<img src="https://raw.githubusercontent.com/alexSFfeng/StickiePlus/develop/readMeImages/DatePickerDemo.png" width="800">

#### How boxes are sorted
---
Selected boxes would be sorted first then unselected boxes would be sorted. REGARDLESS of priority level, tasks with earlier deadlines would be sorted first **(toward the top if sorting base on higher priority, toward the bottom if sorting base on lower priority)**

Daily task boxes are solely sorted base on which box was assigned earlier in the day.

### Memo area
---
This is just a text area for user to type notes or memo

### Calendar
---
Display the days of a month and tasks that are due in this month would also appear on the calendar. Calendar only goes +/- 10 years from today. Refresh button is to refresh the calendar after updating one or more task specification in the "Future Goal and Tasks Panel." Double clicking a day that contains tasks would create a popup display window that shows all the tasks due on that selected date.

<img src="https://raw.githubusercontent.com/alexSFfeng/StickiePlus/develop/readMeImages/PopupDisplay.png" width="800">

### Miscellaneous
This project is meant for organizing user's schedule and is free for modifying and using.

To use: Download jar file and put into local folder; saved data would be generated in the folder where Stickie+.jar is located.

### Acknowledgement
This project is develop using Eclipse, Java swing, and an external library (JCalendar).

Credits to Kai Toedter [jcalendar library link](https://toedter.com/jcalendar/)
Date picking functionality was built on top of his JDateChooser class.

