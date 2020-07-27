from django.http import JsonResponse, HttpResponseForbidden
from django.contrib.auth.models import User

def readAllUsers(request):
    if request.user.is_authenticated:

        result = { "users": [] }

        for user in User.objects.all():
            result["users"].append({
                "userID": user.id,
                "username": user.username,
                "email": user.email,
                "isAdmin": user.is_superuser
            })

        return JsonResponse(result)

    else:
        return HttpResponseForbidden("Forbidden")
