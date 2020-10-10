from django.http import JsonResponse, HttpResponseForbidden
from django.contrib.auth.models import User

def read(request):
    if request.user.is_authenticated:

        result = { "users": [] }

        for user in User.objects.all():
            if user.id != request.user.id:
                result["users"].append({
                    "userID": user.id,
                    "username": user.username,
                    "email": user.email,
                    "isAdmin": user.is_superuser
                })

        return JsonResponse(result)

    else:
        return HttpResponseForbidden("Not authorized")
