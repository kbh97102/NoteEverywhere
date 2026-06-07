# AGENTS.md

You are working on the noteEveryWhere development project.

This is a Kotlin Multiplatform / Compose Multiplatform app targeting Android, iOS, and macOS.

Before implementing, read and follow the planning vault:

```text
/Users/gangbohun/Desktop/기획서/noteEveryWhere
```

## Required Planning References

Product and requirements:

- `/Users/gangbohun/Desktop/기획서/noteEveryWhere/README.md`
- `/Users/gangbohun/Desktop/기획서/noteEveryWhere/01-planning/product-brief.md`
- `/Users/gangbohun/Desktop/기획서/noteEveryWhere/01-planning/requirements.md`

Design and screen specs:

- `/Users/gangbohun/Desktop/기획서/noteEveryWhere/02-design/information-architecture.md`
- `/Users/gangbohun/Desktop/기획서/noteEveryWhere/02-design/macos-home-screen.md`
- `/Users/gangbohun/Desktop/기획서/noteEveryWhere/02-design/macos-home-feature-spec.md`
- `/Users/gangbohun/Desktop/기획서/noteEveryWhere/02-design/task-create-screen.md`
- `/Users/gangbohun/Desktop/기획서/noteEveryWhere/02-design/task-edit-screen.md`
- `/Users/gangbohun/Desktop/기획서/noteEveryWhere/02-design/task-swipe-delete.md`

Technical planning:

- `/Users/gangbohun/Desktop/기획서/noteEveryWhere/03-tech/data-model.md`
- `/Users/gangbohun/Desktop/기획서/noteEveryWhere/03-tech/tech-stack.md`
- `/Users/gangbohun/Desktop/기획서/noteEveryWhere/03-tech/architecture.md`
- `/Users/gangbohun/Desktop/기획서/noteEveryWhere/03-tech/module-structure.md`
- `/Users/gangbohun/Desktop/기획서/noteEveryWhere/03-tech/development-guidelines.md`

ADRs:

- `/Users/gangbohun/Desktop/기획서/noteEveryWhere/04-decisions/0001-documentation-workflow.md`
- `/Users/gangbohun/Desktop/기획서/noteEveryWhere/04-decisions/0002-fix-macos-home-screen.md`
- `/Users/gangbohun/Desktop/기획서/noteEveryWhere/04-decisions/0003-fix-task-create-screen.md`
- `/Users/gangbohun/Desktop/기획서/noteEveryWhere/04-decisions/0004-fix-task-edit-screen.md`
- `/Users/gangbohun/Desktop/기획서/noteEveryWhere/04-decisions/0005-defer-search-and-add-delete-confirmation.md`
- `/Users/gangbohun/Desktop/기획서/noteEveryWhere/04-decisions/0006-development-architecture-and-planning-rules.md`

HTML prototypes:

- `/Users/gangbohun/Desktop/기획서/noteEveryWhere/prototypes/macos-home.html`
- `/Users/gangbohun/Desktop/기획서/noteEveryWhere/prototypes/task-create-macos.html`
- `/Users/gangbohun/Desktop/기획서/noteEveryWhere/prototypes/task-edit-macos.html`
- `/Users/gangbohun/Desktop/기획서/noteEveryWhere/prototypes/task-swipe-delete-macos.html`

## Non-Negotiable Rules

The planning documents and HTML prototypes are the implementation source of truth.

Never change planning decisions, UX flows, data model decisions, milestone scope, or prototype layout assumptions without explicit user confirmation.

If a better idea appears during implementation:

1. Stop before changing the plan.
2. Summarize the proposed change.
3. Ask the user for confirmation.
4. Only after confirmation, update the planning document and ADR.
5. Then implement based on the updated document.

Do not change these without confirmation:

- Task data model
- Milestone 1 scope
- Screen structure
- Main HTML prototype layouts
- User flows
- Task create/edit/delete behavior
- Search being deferred from Milestone 1

## Architecture Direction

Use:

- Compose Multiplatform
- Kotlin Multiplatform
- MVI
- Clean Architecture
- Additional presentation modularization
- Separate DesignSystem module
- Feature-specific UI modules
- Shared single DesignSystem across Android, iOS, and macOS

Dependency direction:

```text
presentation -> domain <- data
```

Domain must not depend on presentation or data.
Presentation calls use cases.
Data implements repositories and local data sources.

## Module Direction

Use this role split as the baseline:

- `:shared:domain`
- `:shared:data`
- `:shared:presentation:designsystem`
- `:shared:presentation:home`
- `:shared:presentation:task-create`
- `:shared:presentation:task-edit`
- `:shared:presentation:task-swipe-delete`
- `:app:android`
- `:app:ios`
- `:app:macos`

Module names can be adjusted to fit Gradle conventions, but the role separation must remain.

Android, iOS, and macOS must use the same DesignSystem tokens. Do not create platform-specific color, typography, or shape forks.

## UI Implementation Rules

Split each screen into large sections, then split each section into small components.

Example:

- `MacosHomeScreen`
  - `HomeTopBar`
  - `HomeSidebar`
  - `HomeSummarySection`
  - `TaskListSection`
  - `TaskDetailSection`

Inside `TaskListSection`:

- `TaskTimeGroup`
- `TaskRow`
- `TaskSwipeDeleteAction`
- `TaskProgressChip`

Do not put too many components in one file.

Recommended roles:

- Screen file: screen composition
- Section file: large screen area
- Component file: reusable small UI
- Contract file: State, Intent, Effect
- ViewModel file: MVI state management
- PreviewData file: preview/sample data

## Compose Rules

- Keep composables stateless when possible.
- Screen composables receive state and emit callbacks or intents.
- Do not call repositories directly from composables.
- Do not put domain logic inside UI components.
- Use DesignSystem tokens instead of hardcoded design values.
- Use the same DesignSystem tokens across Android, iOS, and macOS.
- Handle platform differences through adaptive layout, not separate themes.
- Keep preview/sample data separate from domain logic.

## MVI Rules

Define separate contracts per feature:

- State
- Intent
- Effect

Examples:

- `HomeState`, `HomeIntent`, `HomeEffect`
- `TaskCreateState`, `TaskCreateIntent`, `TaskCreateEffect`
- `TaskEditState`, `TaskEditIntent`, `TaskEditEffect`

Flow:

1. Render State
2. Receive user action
3. Dispatch Intent
4. Run UseCase
5. Update State
6. Handle Effect

## Milestone 1 Data Model

Task is the core entity.
Schedule is the time condition inside Task.

Task:

- `id`: required stable ID
- `title`: required summary
- `memo`: optional detail
- `progress`: required
- `schedule`: optional
  - `startAt`: optional
  - `dueAt`: optional

Progress:

- `todo`
- `inProgress`
- `done`

New Tasks must start with empty `schedule.startAt` and empty `schedule.dueAt`.
Do not auto-fill the current home screen date into a new Task schedule.

## Milestone 1 Screens

Implement:

- macOS Home screen
- Task Create modal
- Task Edit modal
- Task Swipe Delete

Exclude from Milestone 1:

- Search
- Search empty state
- Tags
- Priority
- Notifications
- Repeat settings
- Subtasks
- Linked documents
- Delete confirmation dialog
- Undo toast after delete

## Work Start Checklist

Before implementation:

1. Read the required planning documents and HTML prototypes.
2. Inspect the current development project structure.
3. Create an implementation plan that does not conflict with planning.
4. Propose module and file structure first when structural changes are needed.
5. Implement only after the plan is aligned with the existing planning.

If implementation requires changing planning, stop and ask for user confirmation first.

