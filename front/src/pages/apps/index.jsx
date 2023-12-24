import { lazy } from 'react';
import { Outlet, Route, Routes } from 'react-router-dom';

const Calendar = lazy(() => import('./Calendar'));
const Chat = lazy(() => import('./Chat'));
const CRM = lazy(() => import('./crm'));
const Ecommerce = lazy(() => import('./ecommerce'));
const Email = lazy(() => import('./email'));
const Projects = lazy(() => import('./projects'));
const ReferenceRoom = lazy(() => import('./referenceRoom'));
const SocialFeed = lazy(() => import('./SocialFeed'));
const Tasks = lazy(() => import('./tasks'));
const FileManager = lazy(() => import('./FileManager'));
const Subject = lazy(() => import('./Subject'));

export default function Apps() {
	return (
		<Routes>
			<Route path="/*" element={<Outlet />}>
				<Route path="calendar" element={<Calendar />} />
				<Route path="subjects/:subjectId/chat" element={<Chat />} />
				<Route path="subjects/:subjectId/crm/*" element={<CRM />} />
				<Route path="subjects/:subjectId/ecommerce/*" element={<Ecommerce />} />
				<Route path="subjects/:subjectId/email/*" element={<Email />} />
				<Route path="subjects/:subjectId/projects/*" element={<Projects />} />
				<Route path="subjects/:subjectId/referenceRoom/*" element={<ReferenceRoom />} />
				<Route path="subjects/:subjectId/social" element={<SocialFeed />} />
				<Route path="subjects/:subjectId/tasks/*" element={<Tasks />} />
				<Route path="subjects/:subjectId/file" element={<FileManager />} />
				<Route path="subjects/*" element={<Subject />} />
			</Route>
		</Routes>
	);
}
