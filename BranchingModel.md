# HomeHelper Branching Model

## Master Branch

We consider `master` to be the main branch where the source code of `HEAD`always reflects a production-ready state.

## Develop Branch

We consider `develop` to be the main branch where the source code of `HEAD` always reflects a state with the latest delivered development changes for the next release.

## Feature branches

We create a new `feature` branch to implement each new feature from the backlog.

* May branch off from: `develop`
* Must merge back into: `develop`
* Naming convention: `Feature-{Description}-{PivotalID}`

#### Creating a feature branch

```
# Always branch from develop
$ git checkout develop

# Create branch
$ git checkout -b Feature-{Description}-{PivotalID} develop
```

#### Incorporating a finished feature on develop

```
# Always merge to develop
$ git checkout develop

# Create a new commit object to avoid losing information
$ git merge --no-ff Feature-{Description}-{PivotalID}

# Delete branch
$ git push -d origin Feature-{Description}-{PivotalID}
$ git branch -d Feature-{Description}-{PivotalID}

# Push changes
$ git push -u origin develop
```
