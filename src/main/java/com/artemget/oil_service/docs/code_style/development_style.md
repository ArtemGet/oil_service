# Microservice CodeStyle rules

##Development process:
In this project all development allowed only in feature branches. When development done maintainer supposed to create 
pull-request to master(or not supposed to if there are not any other maintainers in project) and squash all feature commits to one.

##Branching process:
###Branch format:
```
[branch_type]/[Service_Name][task_number]-[task_desription]
```
__For example, maintainer№1 wants to add 42th feature in Oil-Service, that will provide answer to all customer's questions.
He would create branch:__
```
feature/OS42-meaning_of_life
```

__Maintainer№1 successfully finished and merged his task,
but maintainer№2 accidentally found a critical bag after review,
and now he wants to fix it to avoid errors in production.
He would create branch:__
```
hotfix/OS43-make-prod-great-again
```

PS:This staff would be actual only with CI/CD, but anyway, I decided to integrate this standard in my project.

##Commits:
###Commit format:
```
[[Service_name]-[task_number]] [commit_description]
```
###Commit example:
```
[OS-42] add meaning service and life services
```
