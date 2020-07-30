from django.urls import path
from .endpoints import login
from .endpoints import logout
from .endpoints.profile import profile
from .endpoints import signup
from .endpoints.users import users
from .endpoints.user import user

urlpatterns = [
    path('login', login.login),
    path('logout', logout.logout),
    path('profile', profile.profile),
    path('signup', signup.signup),

    path('users', users.users),
    path('user', user.user)

    # games

]
