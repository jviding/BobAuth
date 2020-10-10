from django.http import JsonResponse, HttpResponseForbidden, HttpResponseNotFound
from django.contrib.auth.models import User
import json

def delete(request):
    if request.user.is_authenticated:

        userID = json.loads(request.body)['userID']

        try:
            user = User.objects.get(id=userID)

            if user.id != request.user.id:
                user.delete()

            return JsonResponse({})

        except User.DoesNotExist:
            return HttpResponseNotFound("No matching user found")

    else:
        return HttpResponseForbidden("Not authorized")
