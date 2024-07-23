rootProject.name = "application"

include("home")
include("home:plugin")

findProject(":home:plugin")?.name = "plugin"
include("home:api")

findProject(":home:api")?.name = "api"

include("home:common")
findProject(":home:common")?.name = "common"
include("home:common")
findProject(":home:common")?.name = "common"
