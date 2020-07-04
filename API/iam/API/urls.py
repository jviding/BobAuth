from django.urls import path
from .endpoints import login
from .endpoints import logout
from .endpoints import profile
from .endpoints import signup

urlpatterns = [
    path('login', login.login),
    path('logout', logout.logout),
    path('profile', profile.profile),
    path('signup', signup.signup),

    # me

    # getUsers
    # getUser
    # editUser
    # removeUser

]
