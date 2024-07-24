rootProject.name = "application"

include("home")
include("home:api")
include("home:plugin")

findProject(":home:api")?.name = "api"
findProject(":home:plugin")?.name = "plugin"
