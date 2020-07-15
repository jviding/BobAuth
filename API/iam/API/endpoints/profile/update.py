from django.http import JsonResponse, HttpResponseForbidden
from django.contrib import auth
import json

def updateProfile(request):
    # TODO: email validation
    # TODO: password validation
    if request.user.is_authenticated:

        password = json.loads(request.body)['password']
        if request.user.check_password(password):

            newEmail = json.loads(request.body)['newEmail']
            if newEmail:
                request.user.email = newEmail

            newPassword = json.loads(request.body)['newPassword']
            if newPassword:
                request.user.set_password(newPassword)

            request.user.save()

            # Password change logs out all sessions
            if newPassword:
                auth.login(request, request.user)

            return JsonResponse({
                "userID": request.user.id,
                "username": request.user.username,
                "email": request.user.email,
                "isAdmin": request.user.is_superuser
            })
        else:
            return HttpResponseForbidden("Forbidden")
    else:
        return HttpResponseForbidden("Forbidden")
