# HomeHelper Branching Model

## Master Branch
We consider `master` to be the main branch where the source code of `HEAD`always reflects a production-ready state.

## Develop Branch
We consider `develop` to be the main branch where the source code of `HEAD` always reflects a state with the latest delivered development changes for the next release.

## Feature branches
* May branch off from: `develop`
* Must merge back into: `develop`
* Naming convention: `Feature-{PivotalID}-{Description}`

#### Creating a feature branch
```
_# Always branch from develop_
$ git checkout develop

_# Create branch_
$ git checkout -b **Feature-{PivotalID}-{Description}** develop
```

#### Incorporating a finished feature on develop
```
_# Always merge to develop_
$ git checkout develop

_# Create a new commit object to avoid losing information_
$ git merge --no-ff **Feature-{PivotalID}-{Description}**

_# Delete branch_
$ git branch -d **Feature-{PivotalID}-{Description}**

_# Push changes_
$ git push -u origin develop
```
